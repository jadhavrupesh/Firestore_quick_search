package com.example.firestore_quick_search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFirestore {

    public static HashMap<String, String> realList;

    public static List<String> getField(HashMap<String, String> hashMap, String SearchKey) {
        String fVal;
        SearchKey = SearchKey.toUpperCase();
        List<String> field = new ArrayList<>();
        for (int i = 0; i < hashMap.size(); i++) {
            fVal = getHashMapKeyFromIndex(hashMap, i).toUpperCase();
            if (fVal.contains(SearchKey)) {
                field.add(getHashMapKeyFromIndex(hashMap, i));
            }
        }
        return field;

    }

    public static List<Name> getFieldValue(HashMap<String, Name> hashMap, String SearchKey) {
        String fVal, key;
        SearchKey = SearchKey.toUpperCase();
        List<Name> fieldValue = new ArrayList<>();
        for (int i = 0; i < hashMap.size(); i++) {
            fVal = getHashMapKeyFromIndex(hashMap, i).toUpperCase();
            if (fVal.contains(SearchKey)) {
                key = getHashMapKeyFromIndex(hashMap, i);
                Name name = hashMap.get(key);
                fieldValue.add(name);
            }
        }

        //Sorting List...
        Collections.sort(fieldValue, (name, t1) -> name.getName().compareTo(t1.getName()));

        return fieldValue;


    }


    public static String getHashMapKeyFromIndex(HashMap hashMap, int index) {

        String key = null;
        HashMap<String, Object> hs = hashMap;
        int pos = 0;
        for (Map.Entry<String, Object> entry : hs.entrySet()) {
            if (index == pos) {
                key = entry.getKey();
            }
            pos++;
        }
        return key;

    }

}
