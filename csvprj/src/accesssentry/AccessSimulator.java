package accesssentry;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static accesssentry.AccessSentry.MAX_COMPONENTS;
import static accesssentry.Component.COMPONENT_NAME_PREFIX;
import static accesssentry.Component.DEFAULT_COMPONENT_NAME;

class AccessSimulator {

    Map<String, List<LocalDateTime>> seedAccessToComponents(Map<String, Component> componentsMap) {
        Map<String, List<LocalDateTime>> accessLog = new TreeMap<>();
        for (int accessIdx = 0; accessIdx < 100; accessIdx++) {
            String accessedComponentName = generateAccessedComponentName();
            if (!componentsMap.containsKey(accessedComponentName)) {
                accessedComponentName = DEFAULT_COMPONENT_NAME;
            }
            List<LocalDateTime> accessTime = accessLog.getOrDefault(accessedComponentName, new ArrayList<>());
            accessTime.add(LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextLong(1, 365)));
            // sort the access time list after adding each timestamp for the better visibility; may be commented for the better performance
            Collections.sort(accessTime);
            accessLog.put(accessedComponentName, accessTime);
        }

        return accessLog;
    }

    String generateAccessedComponentName() {
        int nameIndex = ThreadLocalRandom.current().nextInt(1, MAX_COMPONENTS+1);
        return COMPONENT_NAME_PREFIX + nameIndex;
    }
}
