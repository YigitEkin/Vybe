import React, { useState } from "react";
import HomeCheckedIn from "./HomeCheckedIn";
import HomeNotCheckedIn from "./HomeNotCheckedIn";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { useCheckedInStore } from "../../stores/CheckedInStore";
import ProfileDetails from "./ProfileDetails";

const HomePageWrapper = () => {
  const { isCheckIn } = useCheckedInStore();
  const Stack = createNativeStackNavigator();

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
  return (
    <Stack.Navigator screenOptions={screenOptions}
      initialRouteName={isCheckIn ? "HomeCheckedIn" : "HomeNotCheckedIn"}>
      {
        isCheckIn ? (
          <Stack.Screen
            options={{
              headerShown: false,
              headerBackTitleVisible: false,
            }}
            name="HomeCheckedIn"
            component={HomeCheckedIn}
          />
        ) : (
          <Stack.Screen
            options={{
              headerShown: false,
              headerBackTitleVisible: false,
            }}
            name="HomeNotCheckedIn"
            component={HomeNotCheckedIn}
          />
        )
      }
      <Stack.Screen
        name="ProfileDetails"
        component={ProfileDetails}
      />
    </Stack.Navigator>
  );
};

export default HomePageWrapper;