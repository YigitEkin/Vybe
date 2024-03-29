import React from 'react';
import { View, Text, StyleSheet, Image, Platform } from 'react-native';
import { DimensionsHelper } from '../hooks/DimensionsHelper';
import AccountHandlerArea from '../components/HomePage/AccountHandlerArea';
//@ts-ignore
import Icon from '../assets/icon.png';
//@ts-ignore
import HomeScreenImage from '../assets/homePageImage.png';
import * as Font from 'expo-font';

const dimensions = DimensionsHelper();

const HomePage = ({ navigation }: any) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-ExtraBold': require('../assets/fonts/Inter/static/Inter-ExtraBold.ttf'),
  });

  return fontsLoaded ? (
    <View style={styles.container}>
      <View style={styles.icon}>
        <Image style={{ width: 200, height: 100 }} source={Icon} />
      </View>
      <View style={styles.homeScreenImage}>
        <Image source={HomeScreenImage} />
      </View>
      <View style={styles.justifyAndAlignCenter}>
        <Text style={styles.flowWithTheVybeText}>Flow with the Vybe</Text>
      </View>
      <View style={styles.AccountHandlerArea}>
        <AccountHandlerArea navigation={navigation} />
      </View>
    </View>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
    alignItems: 'center',
    width: '100%',
  },
  icon: {
    alignSelf: 'center',
    marginTop: dimensions.isLargerDevice
      ? Platform.OS === 'android'
        ? 100
        : 75
      : 50,
  },
  AccountHandlerArea: {
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 'auto',
    marginBottom: 50,
    width: '80%',
  },
  justifyAndAlignCenter: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  flowWithTheVybeText: {
    color: '#fff',
    fontSize: 25,
    fontFamily: 'Inter-ExtraBold',
  },
  homeScreenImage: {
    alignSelf: 'center',
  },
});

export default HomePage;
