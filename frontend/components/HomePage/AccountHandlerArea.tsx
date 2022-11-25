import React from "react";
import { Text, StyleSheet, Pressable, Button, View } from "react-native";
import { Colors } from "../../constants/Colors";
import { DimensionsHelper } from "../../hooks/DimensionsHelper";
import CreateAccountButton from "./CreateAccountButton";

const dimensions = DimensionsHelper();

const AccountHandlerArea = () => {
  return (
    <View style={styles.container}>
      <CreateAccountButton />
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
  },
  logInText: {
    color: Colors.purple.primary,
    fontSize: 18,
    marginLeft: 5,
  },
  logInTextPressed: {
    color: Colors.purple.primary,
    fontSize: 18,
    marginLeft: 5,
    opacity: 0.7,
  },
});

export default AccountHandlerArea;
