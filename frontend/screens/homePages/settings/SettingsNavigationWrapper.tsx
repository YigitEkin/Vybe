import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import SettingsPage from "./Settings";
import Notifications from "./Notifications";
import { NavigationContainer } from "@react-navigation/native";

const screenOptions = {
  contentStyle: {
    backgroundColor: "#000",
    width: "100%",
  },
  headerStyle: {
    backgroundColor: "#000",
    shadowColor: "transparent",
    height: 10,
  },
  headerTintColor: "#fff",
  headerTitle: "",
};

const SettingsNavigationWrapper = () => {
  const Stack = createNativeStackNavigator();

  return (
    <Stack.Navigator screenOptions={screenOptions} initialRouteName="Profile">
      <Stack.Screen
        options={{
          headerShown: false,
          headerBackTitleVisible: false,
        }}
        name="Profile"
        component={SettingsPage}
      />
      <Stack.Screen name="Notifications" component={Notifications} />
    </Stack.Navigator>
  );
};

export default SettingsNavigationWrapper;
