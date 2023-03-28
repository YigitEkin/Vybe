import React, { useEffect, useState } from "react";
import { Dimensions, Platform, Text, View, Animated } from "react-native";
import MapView, { Marker, AnimatedRegion } from "react-native-maps";
import * as Location from "expo-location";
import * as Permissions from "expo-permissions";
import AnimatedMapRegion from "react-native-maps/lib/AnimatedRegion";

const _getLocationAsync = async () => {
  let { status } = await Permissions.askAsync(Permissions.LOCATION);
  if (status !== "granted") {
    return {
      errorMessage: "Permission to access location was denied",
      success: false,
      location: null,
    };
  }

  let geocode = await Location.getCurrentPositionAsync({});
  let location = await Location.reverseGeocodeAsync(geocode.coords);
  return {
    success: true,
    location,
    errorMessage: null,
  };
};

const { width, height } = Dimensions.get("window");

const MapPage = () => {
  const [markers, setMarkers] = useState<any>([
    {
      title: "Beysu Star",
      description: "Beysu Star",
      coordinate: {
        latitude: 39.87816801608047,
        longitude: 32.70768330187889,
      },
    },
  ]);
  const [location, setLocation] = useState<any>(null);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [success, setSuccess] = useState<boolean>(false);

  const region = new AnimatedRegion({
    latitude: location?.coords?.latitude,
    longitude: location?.coords?.longitude,
    latitudeDelta: location?.coords?.accuracy * 10 ** -3,
    longitudeDelta: location?.coords?.accuracy * 10 ** -3,
  }) as AnimatedMapRegion;

  return success ? (
    <MapView
      region={region}
      style={{ flex: 1 }}
      initialRegion={{
        latitude: location?.coords?.latitude,
        longitude: location?.coords?.longitude,
        latitudeDelta: location?.coords?.accuracy * 10 ** -3,
        longitudeDelta: location?.coords?.accuracy * 10 ** -3,
      }}
      followsUserLocation={false}
      showsUserLocation={true}
      userInterfaceStyle="dark"
    >
      {markers.map((marker: any, index: number) => (
        <Marker
          key={index}
          coordinate={marker.coordinate}
          title={marker.title}
          description={marker.description}
        />
      ))}
    </MapView>
  ) : (
    <View>
      <Text>{errorMsg}</Text>
    </View>
  );
};

export default MapPage;
