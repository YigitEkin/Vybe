import React from 'react';
import { Text, StyleSheet, Pressable, Image } from 'react-native';
import { Colors } from '../../constants/Colors';
import { DimensionsHelper } from '../../hooks/DimensionsHelper';
import * as Font from 'expo-font';

const dimensions = DimensionsHelper();

type StyledButtonProps = {
  onPress?: () => void;
  buttonText?: string;
  style?: any;
  image?: any;
};

const FAAddSongToQueue = ({
  onPress,
  buttonText,
  style,
  image,
}: StyledButtonProps) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
  });

  return fontsLoaded ? (
    <Pressable
      style={({ pressed }) =>
        pressed ? [styles.pressed, style] : [styles.container, style]
      }
      onPress={onPress}
      android_ripple={{ color: Colors.purple.primary }}
    >
      <Text
        style={{
          color: '#fff',
          fontFamily: 'Inter-Medium',
          fontSize: 20,
          marginHorizontal: 10,
        }}
      >
        {buttonText}
      </Text>
    </Pressable>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    height: 50,
    //width: 150,
    backgroundColor: Colors.purple.primary,
    borderRadius: 48,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
  },
  pressed: {
    height: 50,
    backgroundColor: Colors.purple.primary,
    borderRadius: 50,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
    opacity: 0.9,
  },
  text: {
    color: '#fff',
    fontSize: 20,
    fontFamily: 'Inter-Medium',
  },
  imgStyle: {
    width: 70,
    height: 70,
    resizeMode: 'contain',
  },
});

export default FAAddSongToQueue;
