import React from 'react';
import { Text, StyleSheet, Pressable, Image } from 'react-native';
import { Colors } from '../../constants/Colors';
import { DimensionsHelper } from '../../hooks/DimensionsHelper';
import * as Font from 'expo-font';
import QRIcon from '../../assets/QRIcon.png';

const dimensions = DimensionsHelper();

type StyledButtonProps = {
  onPress?: () => void;
  buttonText?: string;
  style?: any;
  image?: any;
};

const FAButton = ({ onPress, buttonText, style, image }: StyledButtonProps) => {
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
      <Image source={QRIcon} />
    </Pressable>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    width: 70,
    height: 70,
    backgroundColor: Colors.purple.primary,
    borderRadius: 100,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
  },
  pressed: {
    width: 70,
    height: 70,
    backgroundColor: Colors.purple.primary,
    borderRadius: 100,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 20,
    opacity: 0.7,
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

export default FAButton;
