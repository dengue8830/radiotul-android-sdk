package com.amla.radiotulsdk.util;

import com.amla.radiotulsdk.company.Company;
import com.amla.radiotulsdk.company.Feature;
import com.amla.radiotulsdk.event.Event;
import com.amla.radiotulsdk.event.EventType;
import com.amla.radiotulsdk.event.Prize;
import com.amla.radiotulsdk.event.Show;
import com.amla.radiotulsdk.event.Trivia;
import com.amla.radiotulsdk.event.TriviaAnswer;
import com.amla.radiotulsdk.event.TriviaQuestion;
import com.amla.radiotulsdk.company.Radio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 *
 * Utility class to all the parse model work of the APIs
 */

public class ModelParser {

    /**
     * Check if that key exists and has value for that object
     * @param jo object to check
     * @param key key to check
     * @return true if exists and has value
     * @throws JSONException
     */
    private static boolean hasValue(JSONObject jo, String key) throws JSONException{
        return jo.has(key) && !jo.isNull(key);
    }

    public static List<Prize> getPrizes(JSONArray jaPremios) throws JSONException {
        List<Prize> prizes = new ArrayList<>();

        for (int i = 0; i < jaPremios.length(); i++) {
            JSONObject joPremio = jaPremios.getJSONObject(i);

            //Si no existen esos valores los damos por true para que la logica que depende de eso funcione.
            //Asumimos que si no estan presentes es porque no importa si no estan habilitados, o porque lo estan pero no hemos enviado esos valores
            //Esta excepcion se agrega para contemplar el caso cuando estamos viendo los prizes de los eventos ganados, estos prizes podrian no estar
            //vigentes pero aun asi se deben mostrar
            boolean estado = hasValue(joPremio, "Estado") ? joPremio.getBoolean("Estado") : true;
            boolean estadoPremio = hasValue(joPremio, "EstadoPremio") ? joPremio.getBoolean("EstadoPremio") : true;

            if (!estado || !estadoPremio)
                continue;

            Prize prize = new Prize();

            prize.setId(joPremio.getLong("Id"));
            prize.setName(joPremio.getString("Nombre"));
            prize.setDescription(joPremio.getString("Descripcion"));
            prize.setPrizeStatus(estadoPremio);
            prize.setActive(estado);

            List<String> imagenes = new ArrayList<>();

            if (hasValue(joPremio, "Imagenes")) {
                JSONArray jaImagenes = joPremio.getJSONArray("Imagenes");

                for (int j = 0; j < jaImagenes.length(); j++) {
                    imagenes.add(jaImagenes.getJSONObject(j).getString("Nombre"));
                }
            }
            prize.setPictures(imagenes);

            prizes.add(prize);
        }

        return prizes;
    }

    public static List<Event> toEvents(JSONArray rawEvents) throws JSONException{
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < rawEvents.length(); i++) {
            Event event = toEvent(rawEvents.getJSONObject(i));
            events.add(event);
        }

        return events;
    }

    /**
     * Convierte el json de evento al modelo de evento con el arbol completo
     * @param joEvento
     * @return
     * @throws JSONException
     */
    public static Event toEvent(JSONObject joEvento) throws JSONException {
        Event event = new Event();
        event.setId(joEvento.getLong("Id"));
        event.setName(joEvento.getString("Titulo"));
        event.setDescription(joEvento.getString("Descripcion"));

        if(hasValue(joEvento, "NumeroConcursantes"))
            event.setContestantsCount(joEvento.getLong("NumeroConcursantes"));

        event.setType(getEventType(joEvento));
        event.setShows(getShows(joEvento));
        event.setTrivia(getTrivia(joEvento));

        if(hasValue(joEvento, "PremiosEvento"))
            event.setPrizes(getPrizes(joEvento.getJSONArray("PremiosEventos")));
        else
            event.setPrizes(new ArrayList<Prize>());

        if(hasValue(joEvento, "FechaGanado"))
            event.setParsedWonDate(joEvento.getString("FechaGanado"));

//        event.setExpanded(false);//necesario para el viewholder
//        //Es para llevar un control local. Ya que del server vienen solo los vigentes
//        event.setFinalizado(false);

        if(hasValue(joEvento, "SegundosRestantes"))
            event.setSecondsRemaining((long) Math.floor(joEvento.getDouble("SegundosRestantes")));

        if(hasValue(joEvento, "EstoyParticipando"))
            event.setiAmParticipating(joEvento.getBoolean("EstoyParticipando"));

        return event;
    }

    private static List<Show> getShows(JSONObject rawEvent) throws JSONException {
        List<Show> shows = new ArrayList<>();

        if(!hasValue(rawEvent, "ProgramasSeleccionado"))
            return shows;

        JSONArray rawShows = rawEvent.getJSONArray("ProgramasSeleccionado");

        for (int i = 0; i < rawShows.length(); i++) {
            JSONObject rawShow = rawShows.getJSONObject(i);

            if (!rawShow.getBoolean("Estado"))
                continue;

            Show show = new Show();
            show.setId(rawShow.getLong("IdPrograma"));
            show.setName(rawShow.getString("NombrePrograma"));

            shows.add(show);
        }

        return shows;
    }

    private static EventType getEventType(JSONObject rawEvent) throws JSONException {
        if(!hasValue(rawEvent, "EventType"))
            return null;

        JSONObject rawEventType = rawEvent.getJSONObject("EventType");

        EventType eventType = new EventType();
        eventType.setId(rawEventType.getLong("Id"));
        eventType.setName(rawEventType.getString("Nombre"));

        return eventType;
    }

    /**
     * Cast a raw json to a Trivia model.
     * The list attributes as Questions never will be null
     * @param joEvento
     * @return
     * @throws JSONException
     */
    private static Trivia getTrivia(JSONObject joEvento) throws JSONException {
        if(!hasValue(joEvento, "Trivia"))
            return null;

        JSONObject rawTrivia = joEvento.getJSONArray("Trivia").getJSONObject(0);

        Trivia trivia = new Trivia();
        trivia.setIdTrivia(rawTrivia.getLong("Id"));
        trivia.setTriviaTypeId(rawTrivia.getLong("IdTipoTrivia"));
        trivia.setTitulo(rawTrivia.getString("Titulo"));

        trivia.setQuestions(new ArrayList<TriviaQuestion>());

        if(!hasValue(rawTrivia, "Preguntas"))
            throw new JSONException("A trivia must hace at last one question");

        JSONArray rawQuestions = rawTrivia.getJSONArray("Preguntas");

        for (int i = 0; i < rawQuestions.length(); i++) {
            JSONObject rawQuestion = rawQuestions.getJSONObject(i);

            TriviaQuestion question = new TriviaQuestion();
            question.setId(rawQuestion.getLong("Id"));
            question.setTriviaId(rawQuestion.getLong("IdTrivia"));
            question.setQuestionText(rawQuestion.getString("Pregunta"));

            question.setAnswers(new ArrayList<TriviaAnswer>());

            if(!hasValue(rawQuestion, "Respuesta"))
                throw new JSONException("A question must have at least one answer");

            JSONArray rawAnswers = rawQuestion.getJSONArray("Respuesta");

            for (int j = 0; j < rawAnswers.length(); j++) {
                JSONObject rawAnswer = rawAnswers.getJSONObject(j);

                TriviaAnswer answer = new TriviaAnswer();
                answer.setId(rawAnswer.getLong("Id"));
                answer.setTriviaId(rawAnswer.getLong("IdTrivia"));
                answer.setAnswerText(rawAnswer.getString("Respuesta"));
                answer.setCorrect(rawAnswer.getBoolean("EstadoCorrecto"));

                question.getAnswers().add(answer);
            }

            trivia.getQuestions().add(question);
        }

        return trivia;
    }

    /**
     * Cast a raw json to a Show model.
     *
     * @param joPrograma
     * @return
     * @throws JSONException
     */
    public static Show toShow(JSONObject joPrograma) throws JSONException{
        Show show = new Show();

        show.setId(joPrograma.getLong("IdPrograma"));
        show.setName(joPrograma.getString("Nombre"));
        show.setStartTime(joPrograma.getString("HoraInicio").substring(0, 5));
        show.setEndTime(joPrograma.getString("HoraFin").substring(0, 5));
        show.setDescription(joPrograma.getString("Descripcion"));
        show.setFacebookUrl(joPrograma.getString("Facebook"));
        show.setTwitterUrl(joPrograma.getString("Twitter"));
        show.setPhone(joPrograma.isNull("TelefonoMovil") ? "" : joPrograma.getString("TelefonoMovil"));
        show.setPhoneWithWhatsapp(joPrograma.getString("TelefonoConWhatsapp"));
        show.setSpeakerName(joPrograma.getString("Locutor"));

        if(hasValue(joPrograma, "Trasmitiendo"))
            show.setBeingBroadcastNow(joPrograma.getBoolean("Trasmitiendo"));

        return show;
    }

    /**
     * Cast a raw json to a Company model.
     * The list attributes as radios and features never will be null
     *
     * @param response
     * @return
     * @throws JSONException
     */
    public static Company toCompany(JSONObject response) throws JSONException{
        JSONObject rawData = response.getJSONArray("JsonDatosEmpresa").getJSONObject(0);
        Company company = new Company();
        company.setId(rawData.getLong("Id"));
        company.setName(rawData.getString("Nombre"));
        company.setAdress(rawData.getString("Direccion"));
        company.setMobilePhone(rawData.getString("TelefonoMovil"));
        company.setLandlinePhone(rawData.getString("TelefonoFijo"));

        company.setRadios(new ArrayList<Radio>());

        if(hasValue(response, "JsonRadios")){
            JSONArray rawRadios = response.getJSONArray("JsonRadios");

            for (int i = 0 ; i < rawRadios.length() ; i++){
                JSONObject item = rawRadios.getJSONObject(i);
                Radio radio = new Radio();
                radio.setId(item.getLong("Id"));
                radio.setName(item.getString("Nombre"));
                radio.setUrlStreaming(item.getString("UrlStreaming"));
                radio.setActive(item.getBoolean("Estado"));
                radio.setPhone(item.getString("Telefono"));
                radio.setPhoneWithWhatsapp(item.getString("TelefonoWhatsApp"));
                radio.setTypeId(item.getLong("IdTipoRadio"));
                radio.setTypeLabel(item.getString("TipoRadio"));

                company.getRadios().add(radio);
            }
        }

        company.setFeatures(new ArrayList<Feature>());

        if(hasValue(response, "funcionalidades")){
            JSONArray rawFeatures = response.getJSONArray("funcionalidades");

            for (int i = 0 ; i < rawFeatures.length() ; i++){
                JSONObject item = rawFeatures.getJSONObject(i);
                Feature feature = new Feature();
                feature.setCodename(item.getString("NombreClave"));
                feature.setActive(item.getBoolean("Activo"));

                company.getFeatures().add(feature);
            }
        }

        return company;
    }
}
