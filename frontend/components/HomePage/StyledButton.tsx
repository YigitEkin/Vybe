import React from "react";
import { Text, StyleSheet, Pressable, Image } from "react-native";
import { Colors } from "../../constants/Colors";
import { DimensionsHelper } from "../../hooks/DimensionsHelper";
import * as Font from "expo-font";

const dimensions = DimensionsHelper();

type StyledButtonProps = {
  onPress: () => void;
  buttonText: string;
  style?: any;
  imgSource?: any;
};

const StyledButton = ({ onPress, buttonText, style, imgSource }: StyledButtonProps) => {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Medium": require("../../assets/fonts/Inter/static/Inter-Medium.ttf"),
  });

  return fontsLoaded ? (
    <Pressable
      style={({ pressed }) =>
        pressed ? [styles.pressed, style] : [styles.container, style]
      }
      onPress={onPress}
      android_ripple={{ color: Colors.purple.primary }}
    >
      <Image style={styles.image} source={imgSource} />
      <Text style={[styles.text, style?.text]}>{buttonText}</Text>
    </Pressable>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    width: "90%",
    height: 50,
    backgroundColor: Colors.purple.primary,
    borderRadius: 100,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 20,
  },
  pressed: {
    width: "90%",
    height: 50,
    backgroundColor: Colors.purple.primary,
    borderRadius: 100,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 20,
    opacity: 0.7,
  },
  text: {
    color: "#fff",
    fontSize: 20,
    fontFamily: "Inter-Medium",
  },
  image: {
    position: "absolute",
    left: 20,
  }
});

export default StyledButton;
