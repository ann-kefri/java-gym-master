package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings {
    private final Coach coach;
    private final int trainingCount;

    public CounterOfTrainings(Coach coach, int trainingCount) {
        this.coach = coach;
        this.trainingCount = trainingCount;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return trainingCount == that.trainingCount && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, trainingCount);
    }

}