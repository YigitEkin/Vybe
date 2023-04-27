import React from 'react';
import { View, Text } from 'react-native';
import LottieView from 'lottie-react-native';

export default function Splash({ setIsLoading }) {
  const lottieRef = React.useRef(null);
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
        //autoPlay
        onLayout={() => {
          lottieRef.current?.play();
        }}
        ref={lottieRef}
        loop={false}
        resizeMode='cover'
        onAnimationFinish={() => setIsLoading(false)}
      />
      <Text style={{ color: 'black' }}>{'ASDASD'}</Text>
    </View>
  );
}
