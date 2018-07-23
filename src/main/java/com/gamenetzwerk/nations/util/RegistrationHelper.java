package com.gamenetzwerk.nations.util;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class RegistrationHelper {

    public void registerListeners(String pckg, JavaPlugin javaPlugin) {
        RegistrationHelper.getPacketObjects("com.gamenetzwerk.nations.listener", Listener.class).forEach(listener ->
                Bukkit.getServer().getPluginManager().registerEvents(
                        (Listener) listener, javaPlugin));
    }

    @SneakyThrows
    private Set<Object> getPacketObjects(String pckg, Class superClass) {
        ClassLoader classLoader = RegistrationHelper.class.getClassLoader();
        Set<Object> objects = new HashSet<>();

        for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader)
                .getTopLevelClassesRecursive(pckg)) {
            Class cls = Class.forName(classInfo.getName(), true, classLoader);

            if (Arrays.asList(cls.getInterfaces()).contains(superClass) ||
                    cls.getSuperclass().equals(superClass)) {
                objects.add(cls.newInstance());
            }
        }

        return Collections.unmodifiableSet(objects);
    }

}
