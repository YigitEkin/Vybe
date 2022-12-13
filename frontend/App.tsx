import { StatusBar } from "expo-status-bar";
import React from "react";
import { StyleSheet, Text, View, SafeAreaView } from "react-native";
import HomePage from "./screens/HomePage";
import { useCallback } from "react";
import { useFonts } from "expo-font";
import * as SplashScreen from "expo-splash-screen";
import SignUpMail from "./screens/signup/SignUpMail";
import SignUpCompletedScreen from "./screens/signup/SignUpCompletedScreen";
import SignUpPassword from "./screens/signup/SignUpPassword";
import SignUpUsername from "./screens/signup/SignupUsername";
import EnterPhoneNumber from "./components/2fa/EnterPhoneNumber";
import Router from "./navigation/Router";


SplashScreen.preventAutoHideAsync();

export default function App() {
  const [fontsLoaded] = useFonts({
    'Inter-Black': require('./assets/fonts/Inter/static/Inter-Black.ttf'),
    'Inter-Bold': require('./assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-ExtraBold': require('./assets/fonts/Inter/static/Inter-ExtraBold.ttf'),
    'Inter-ExtraLight': require('./assets/fonts/Inter/static/Inter-ExtraLight.ttf'),
    'Inter-Light': require('./assets/fonts/Inter/static/Inter-Light.ttf'),
    'Inter-Medium': require('./assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('./assets/fonts/Inter/static/Inter-Regular.ttf'),
    'Inter-SemiBold': require('./assets/fonts/Inter/static/Inter-SemiBold.ttf'),
    'Inter-Thin': require('./assets/fonts/Inter/static/Inter-Thin.ttf'),
  });

  const onLayoutRootView = useCallback(async () => {
    if (fontsLoaded) {
      await SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  return (
    <View style={styles.container} onLayout={onLayoutRootView}>
      <SafeAreaView style={styles.container}>
        <StatusBar style="light" networkActivityIndicatorVisible={true} />
        <Router />
      </SafeAreaView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#000",
    width: "100%",
  },
});
