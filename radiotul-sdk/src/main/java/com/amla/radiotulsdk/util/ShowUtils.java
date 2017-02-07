package com.amla.radiotulsdk.util;

import com.amla.radiotulsdk.event.Show;

import org.json.JSONException;

import java.util.Calendar;
import java.util.List;

/**
 * Created by dengue8830 on 2/3/17.
 *
 * Utility class for the Show model
 */

public class ShowUtils {
    /**
     * Gets the schedule of one show in "day hh:mm - hh:mm" format
     * The day is in spanish
     *
     * @param showId Show of wich we will get the schedule
     * @param weekSchedule The schedule of all the shows. The week schedule
     * @return the humanized representation of the schedule
     * @throws JSONException the exception
     */
    public static String getShowSchedule(long showId, List<List> weekSchedule) throws JSONException{
        StringBuilder showSchedule = new StringBuilder();

        for(int day  = 0 ; day < 7 ; day++){
            List<Show> oneDayShows = weekSchedule.get(day);

            for(Show show : oneDayShows){

                if(show.getId().equals(showId))
                    showSchedule
                            .append(getDayOfTheWeekName(day))
                            .append(" ")
                            .append(show.getStartTime())
                            .append(" - ")
                            .append(show.getEndTime())
                            .append("\n");
            }
        }

        if(showSchedule.toString().isEmpty())
            showSchedule.append("-");

        return showSchedule.toString();
    }

    /**
     * Obtiene el nombre completo del dia de la semana segun la posicion del dia de la semana [0-6]
     * @param day the day on the range [0-6]
     * @return the day of the week on spanish
     */
    public static String getDayOfTheWeekName(int day){
        switch (day){
            case 0:
                return "Lunes";
            case 1:
                return "Martes";
            case 2:
                return "Miércoles";
            case 3:
                return "Jueves";
            case 4:
                return "Viernes";
            case 5:
                return "Sábado";
            case 6:
                return "Domingo";
            default:
                throw new IllegalArgumentException("The parameter must be in the range [0-6]");
        }
    }

    /**
     * Cast the calendar's day position to the tabs day's position.
     * Ej: In Calendar sunday is day 1, and in the array of tabs is 6
     * @param calendarDayOfWeek the day of the Calendar constant
     * @return The tab's day position
     */
    public static int getDay(int calendarDayOfWeek) throws IllegalArgumentException{
        switch (calendarDayOfWeek){
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                throw new IllegalArgumentException("The params must be a java.util.Calendar's day");
        }
    }
}
