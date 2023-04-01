import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import MapPage from "./Map";
import VenueDetails from "./VenueDetails";
import AddVenueReview from "./AddVenueReview";

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

const MapNavigationWrapper = () => {
  const Stack = createNativeStackNavigator();

  return (
    <Stack.Navigator screenOptions={screenOptions} initialRouteName="MapView">
      <Stack.Screen
        options={{
          headerShown: false,
          headerBackTitleVisible: false,
        }}
        name="MapView"
        component={MapPage}
      />
      <Stack.Screen name="VenueDetail" component={VenueDetails} />
      <Stack.Screen name="AddVenueReview" component={AddVenueReview} />
    </Stack.Navigator>
  );
};

export default MapNavigationWrapper;
