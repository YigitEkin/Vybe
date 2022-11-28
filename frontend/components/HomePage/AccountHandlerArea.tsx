import React from "react";
import { Text, StyleSheet, Pressable, Button, View } from "react-native";
import { Colors } from "../../constants/Colors";
import { DimensionsHelper } from "../../hooks/DimensionsHelper";
import StyledButton from "./StyledButton";
import * as Font from "expo-font";

const dimensions = DimensionsHelper();

const AccountHandlerArea = () => {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });

  return (
    <View style={styles.container}>
      {
        //TODO: onPress will navigate the user to the Sign Up page
      }
      <StyledButton onPress={() => {}} buttonText={"Create Account"} />
      {/* Create Account */}
      <View style={styles.row}>
        <Text style={styles.text}>Have an account?</Text>
        {
          //TODO: onPress will navigate the user to the login page
        }
        <Pressable
          style={({ pressed }) =>
            pressed ? styles.logInTextPressed : styles.logInText
          }
        >
          <Text style={styles.logInText}>Log in</Text>
        </Pressable>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
    alignItems: "center",
  },
  row: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    marginTop: 15,
  },
  text: {
    color: "#fff",
    fontSize: 18,
    fontFamily: "Inter-Regular",
  },
  logInText: {
    color: Colors.purple.primary,
    fontSize: 18,
    marginLeft: 5,
    fontFamily: "Inter-Regular",
  },
  logInTextPressed: {
    color: Colors.purple.primary,
    fontSize: 18,
    marginLeft: 5,
    opacity: 0.7,
    fontFamily: "Inter-Regular",
  },
});

export default AccountHandlerArea;
