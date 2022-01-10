package json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    final private LinkedHashMap<String, Json> pairHashMap = new LinkedHashMap();

    public JsonObject(JsonPair... jsonPairs) {
        // Doing not inline to preserve order of keys added
        for(JsonPair pair: jsonPairs){
            pairHashMap.put(pair.key, pair.value);
        }
    }

    @Override
    public String toJson() {
        return "{" + getJsonBody() + "}";
    }

    private String getJsonBody() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Json>> pairHashMapIterator = pairHashMap.entrySet().iterator();
        while (pairHashMapIterator.hasNext()) {
            Map.Entry<String, Json> entry = pairHashMapIterator.next();
            stringBuilder.append(String.format("'%s': %s", entry.getKey(), entry.getValue().toJson()));
            if (pairHashMapIterator.hasNext()) {
                stringBuilder.append(", ");
            }

        }
        return stringBuilder.toString();
    }

    public void add(JsonPair jsonPair) {
        pairHashMap.put(jsonPair.key, jsonPair.value);
    }

    public Json find(String name) {
        return pairHashMap.get(name);
    }

    public JsonObject projection(String... names) {
        JsonObject projectionItems = new JsonObject();
        for (String name :
                names) {
            if (!pairHashMap.containsKey(name))
                continue;
            JsonPair jsonPair = new JsonPair(name, pairHashMap.get(name));
            projectionItems.add(jsonPair);
        }
        return projectionItems;
    }
}
