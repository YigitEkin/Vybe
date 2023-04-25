import React from 'react';
import { View, Text } from 'react-native';
import LottieView from 'lottie-react-native';

export default function Splash({ setIsLoading }) {
  return (
    <View
      style={{
        flex: 1,
        alignItems: 'center',
        margin: 0,
        backgroundColor: 'black',
      }}
    >
      <LottieView
        source={require('../assets/splashScreen.json')}
        autoPlay
        loop={false}
        resizeMode='cover'
        onAnimationFinish={() => setIsLoading(false)}
      />
      <Text style={{ color: 'black' }}>{'ASDASD'}</Text>
    </View>
  );
}
