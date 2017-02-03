package com.amla.radiotulsdktestcase.util;

import com.amla.radiotulsdktestcase.events.Event;
import com.amla.radiotulsdktestcase.events.EventType;
import com.amla.radiotulsdktestcase.events.PreguntaTrivia;
import com.amla.radiotulsdktestcase.events.Prize;
import com.amla.radiotulsdktestcase.events.Show;
import com.amla.radiotulsdktestcase.events.RespuestaTrivia;
import com.amla.radiotulsdktestcase.events.Trivia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class ModelParser {

    /**
     * Verifica si esa clave existe y tiene valor para ese jsonobject
     * @param jo objeto a revisar
     * @param key clave a revisar
     * @return true si existe y tiene valor, false en otro caso
     * @throws JSONException
     */
    private static boolean tieneValor(JSONObject jo, String key) throws JSONException{
        return jo.has(key) && !jo.isNull(key);
    }

    public static List<Prize> getPremios(JSONArray jaPremios) throws JSONException {
        List<Prize> prizes = new ArrayList<>();

        for (int i = 0; i < jaPremios.length(); i++) {
            JSONObject joPremio = jaPremios.getJSONObject(i);

            //Si no existen esos valores los damos por true para que la logica que depende de eso funcione.
            //Asumimos que si no estan presentes es porque no importa si no estan habilitados, o porque lo estan pero no hemos enviado esos valores
            //Esta excepcion se agrega para contemplar el caso cuando estamos viendo los prizes de los eventos ganados, estos prizes podrian no estar
            //vigentes pero aun asi se deben mostrar
            boolean estado = tieneValor(joPremio, "Estado") ? joPremio.getBoolean("Estado") : true;
            boolean estadoPremio = tieneValor(joPremio, "EstadoPremio") ? joPremio.getBoolean("EstadoPremio") : true;

            if (!estado || !estadoPremio)
                continue;

            Prize prize = new Prize();

            prize.setId(joPremio.getInt("Id"));
            prize.setName(joPremio.getString("Nombre"));
            prize.setDescription(joPremio.getString("Descripcion"));
            prize.setPrizeStatus(estadoPremio);
            prize.setActive(estado);

            List<String> imagenes = new ArrayList<>();

            if (tieneValor(joPremio, "Imagenes")) {
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

    public static List<Event> toEvents(JSONArray eventsArray) throws JSONException{
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < eventsArray.length(); i++) {
            Event event = toEvento(eventsArray.getJSONObject(i));
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
    public static Event toEvento(JSONObject joEvento) throws JSONException {
        Event event = new Event();
        event.setId(joEvento.getInt("Id"));
        event.setName(joEvento.getString("Titulo"));
        event.setDescription(joEvento.getString("Descripcion"));

        if(tieneValor(joEvento, "NumeroConcursantes"))
            event.setContestantsCount(joEvento.getInt("NumeroConcursantes"));

        event.setType(getTipoEvento(joEvento));
        event.setShows(getProgramas(joEvento));
        event.setTrivia(getTrivia(joEvento));

        if(tieneValor(joEvento, "PremiosEvento"))
            event.setPrizes(getPremios(joEvento.getJSONArray("PremiosEventos")));
        else
            event.setPrizes(new ArrayList<Prize>());

        if(tieneValor(joEvento, "FechaGanado"))
            event.setParsedWonDate(joEvento.getString("FechaGanado"));

//        event.setExpanded(false);//necesario para el viewholder
//        //Es para llevar un control local. Ya que del server vienen solo los vigentes
//        event.setFinalizado(false);

        if(tieneValor(joEvento, "SegundosRestantes"))
            event.setSecondsRemaining((long) Math.floor(joEvento.getDouble("SegundosRestantes")));

        if(tieneValor(joEvento, "EstoyParticipando"))
            event.setiAmParticipating(joEvento.getBoolean("EstoyParticipando"));

        return event;
    }

    public static List<Show> getProgramas(JSONObject joEvento) throws JSONException {
        List<Show> shows = new ArrayList<>();

        if(!tieneValor(joEvento, "ProgramasSeleccionado"))
            return shows;

        JSONArray jaProgramas = joEvento.getJSONArray("ProgramasSeleccionado");

        for (int i = 0; i < jaProgramas.length(); i++) {
            JSONObject joPrograma = jaProgramas.getJSONObject(i);

            if (!joPrograma.getBoolean("Estado"))
                continue;

            Show show = new Show();
            show.setId(joPrograma.getInt("IdPrograma"));
            show.setName(joPrograma.getString("NombrePrograma"));

            shows.add(show);
        }

        return shows;
    }

    public static EventType getTipoEvento(JSONObject joEvento) throws JSONException {
        if(!tieneValor(joEvento, "EventType"))
            return null;

        JSONObject joTipoEvento = joEvento.getJSONObject("EventType");

        EventType eventType = new EventType();
        eventType.setId(joTipoEvento.getInt("Id"));
        eventType.setName(joTipoEvento.getString("Nombre"));

        return eventType;
    }

    public static Trivia getTrivia(JSONObject joEvento) throws JSONException {
        if(!tieneValor(joEvento, "Trivia"))
            return null;

        JSONArray jaTrivia = joEvento.getJSONArray("Trivia");

        if(jaTrivia.length() == 0)
            return null;

        Trivia trivia = new Trivia();
        trivia.setIdTrivia(jaTrivia.getJSONObject(0).getInt("Id"));
        trivia.setIdTipoTrivia(jaTrivia.getJSONObject(0).getInt("IdTipoTrivia"));
        trivia.setTitulo(jaTrivia.getJSONObject(0).getString("Titulo"));

        List<PreguntaTrivia> preguntasTrivia = new ArrayList<>();
        JSONArray jsonPreguntas = jaTrivia.getJSONObject(0).getJSONArray("Preguntas");

        for (int i = 0; i < jsonPreguntas.length(); i++) {
            JSONObject jsonPregunta = jsonPreguntas.getJSONObject(i);

            PreguntaTrivia pregunta = new PreguntaTrivia();
            pregunta.setIdPregunta(jsonPregunta.getInt("Id"));
            pregunta.setIdTrivia(jsonPregunta.getInt("IdTrivia"));
            pregunta.setPregunta(jsonPregunta.getString("Pregunta"));

            List<RespuestaTrivia> respuestasTrivia = new ArrayList<>();
            JSONArray jsonRespuestas = jsonPregunta.getJSONArray("Respuesta");

            for (int j = 0; j < jsonRespuestas.length(); j++) {
                JSONObject jsonRespuesta = jsonRespuestas.getJSONObject(j);

                RespuestaTrivia respuesta = new RespuestaTrivia();
                respuesta.setIdRespuesta(jsonRespuesta.getInt("Id"));
                respuesta.setIdTrivia(jsonRespuesta.getInt("IdTrivia"));
                respuesta.setRespuesta(jsonRespuesta.getString("Respuesta"));
                respuesta.setEstadoCorrecto(jsonRespuesta.getBoolean("EstadoCorrecto"));

                respuestasTrivia.add(respuesta);
            }
            pregunta.setRespuestasTrivia(respuestasTrivia);

            preguntasTrivia.add(pregunta);
        }
        trivia.setPreguntasTrivia(preguntasTrivia);

        return trivia;
    }
}
