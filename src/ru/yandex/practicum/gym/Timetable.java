package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek,TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay =  trainingSession.getTimeOfDay();

        if(!timetable.containsKey(dayOfWeek)){
            timetable.put(dayOfWeek,new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.get(dayOfWeek);

        if(!daySessions.containsKey(timeOfDay)){
            daySessions.put(timeOfDay, new ArrayList<>());
        }

        List<TrainingSession> timeSessions = daySessions.get(timeOfDay);
        timeSessions.add(trainingSession);

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

        if (sessionsForDay == null || sessionsForDay.isEmpty()){
            return new ArrayList<>();
        }

        List<TrainingSession> allSessions = new ArrayList<>();

        NavigableSet<TimeOfDay> sortedTimes = sessionsForDay.navigableKeySet();
        for (TimeOfDay time : sortedTimes){
            List<TrainingSession> sessions = sessionsForDay.get(time);
            allSessions.addAll(sessions);
        }

        return allSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

        if (sessionsForDay == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = sessionsForDay.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sessions);
    }

    public List<CounterOfTrainings> getCountByCoaches () {
        HashMap<Coach, Integer> countByCoaches = getCoachIntegerHashMap();

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countByCoaches.entrySet()){
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        result.sort(new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return Integer.compare(o2.getTrainingCount(), o1.getTrainingCount());
            }
        });

        return result;
    }

    private HashMap<Coach, Integer> getCoachIntegerHashMap() {
        HashMap<Coach, Integer> countByCoaches = new HashMap<>();
        for (Map.Entry<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> dayEntry : timetable.entrySet()){
            TreeMap<TimeOfDay, List<TrainingSession>> sessionsByTime = dayEntry.getValue();
            for (Map.Entry<TimeOfDay, List<TrainingSession>> timeEntry : sessionsByTime.entrySet()){
                List<TrainingSession> sessions = timeEntry.getValue();
                for (TrainingSession session : sessions){
                    Coach coach = session.getCoach();
                    countByCoaches.put(coach, countByCoaches.getOrDefault(coach, 0) + 1 );
                }
            }
        }
        return countByCoaches;
    }

}
