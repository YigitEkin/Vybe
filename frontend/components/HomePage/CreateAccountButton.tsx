import React from "react";
import { Text, StyleSheet, Pressable } from "react-native";
import { Colors } from "../../constants/Colors";
import { DimensionsHelper } from "../../hooks/DimensionsHelper";

const dimensions = DimensionsHelper();

const CreateAccountButton = () => {
  return (
    //TODO: onPress will navigate the user to the create an account page
    <Pressable
      style={({ pressed }) => (pressed ? styles.pressed : styles.container)}
      onPress={() => {}}
      android_ripple={{ color: Colors.purple.primary }}
    >
      <Text style={styles.text}>Create Account</Text>
    </Pressable>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 250,
    height: 50,
    backgroundColor: Colors.purple.primary,
    borderRadius: 100,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 20,
  },
  pressed: {
    width: 250,
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
  },
});

export default CreateAccountButton;
