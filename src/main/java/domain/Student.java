package domain;

import json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class Student extends BasicStudent {
    private List<Tuple<String, Integer>> exams;

    public Student(String name, String surname, Integer year, Tuple<String, Integer>... exams) {
        super(name, surname, year);
        this.exams = Arrays.stream(exams).collect(Collectors.toList());
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject parentJsonObject = super.toJsonObject();
        JsonObject[] objects = exams.stream().map(t ->
             new JsonObject(
                    new JsonPair("course", new JsonString(t.key)),
                    new JsonPair("mark", new JsonNumber(t.value)),
                    new JsonPair("passed", new JsonBoolean(t.value > 2))

            )
        ).toArray(JsonObject[]::new);


        parentJsonObject.add(new JsonPair("exams", new JsonArray(objects)));

        return parentJsonObject;
    }
}