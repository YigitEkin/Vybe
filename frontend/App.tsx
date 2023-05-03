import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { StyleSheet, Text, View, SafeAreaView } from 'react-native';
import HomePage from './screens/HomePage';
import { useCallback, useState } from 'react';
import { useFonts } from 'expo-font';
import * as SplashScreen from 'expo-splash-screen';
import SignUpMail from './screens/signup/SignUpMail';
import SignUpCompletedScreen from './screens/signup/SignUpCompletedScreen';
import SignUpPassword from './screens/signup/SignUpPassword';
import SignUpUsername from './screens/signup/SignupUsername';
import EnterPhoneNumber from './components/2fa/EnterPhoneNumber';
import Splash from './screens/Splash';
import Toast, { BaseToast, ErrorToast } from 'react-native-toast-message';

import Router from './navigation/Router';

//SplashScreen.preventAutoHideAsync();
const toastConfig = {
  /*
    Overwrite 'success' type,
    by modifying the existing `BaseToast` component
  */
  success: (props) => (
    <BaseToast
      {...props}
      style={{ borderLeftColor: 'green' }}
      //contentContainerStyle={{ paddingHorizontal: 15 }}
      text1Style={{
        fontSize: 15,
        fontWeight: '400',
      }}
      text2Style={{
        fontSize: 15,
        fontWeight: '400',
      }}
    />
  ),
  error: (props) => (
    <ErrorToast
      {...props}
      text1Style={{
        fontSize: 15,
        fontWeight: '400',
      }}
      text2Style={{
        fontSize: 15,
        fontWeight: '400',
      }}
    />
  ),
};
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

  const [isLoading, setIsLoading] = useState(true);
  const onLayoutRootView = useCallback(async () => {
    if (fontsLoaded) {
      await SplashScreen.hideAsync();
    }
  }, [fontsLoaded]);

  if (!fontsLoaded) {
    return null;
  }

  return isLoading ? (
    <Splash setIsLoading={setIsLoading} />
  ) : (
    <>
      <SafeAreaView style={styles.container} />
      {/*<SafeAreaView style={styles.container2}>*/}
      <View
        style={{ flex: 1, backgroundColor: '#000', width: '100%' }}
        onLayout={onLayoutRootView}
      >
        <StatusBar style='light' networkActivityIndicatorVisible={true} />
        <Router />
      </View>
      {/*</SafeAreaView>*/}
      <Toast config={toastConfig} />
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 0,
    backgroundColor: '#000',
    width: '100%',
  },
  container2: {
    flex: 1,
    backgroundColor: '#6C7072',
  },
});
