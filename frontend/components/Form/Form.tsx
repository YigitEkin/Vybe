import React from "react";
import {
  Text,
  StyleSheet,
  Pressable,
  View,
  ScrollView,
  KeyboardAvoidingView,
} from "react-native";
import StyledButton from "../HomePage/StyledButton";
import * as Font from "expo-font";
import StatusBar from "./StatusBar";

export type FormItem = {
  label: string;
  component: JSX.Element;
};

type FormProps = {
  items: FormItem[];
  currentStep: number;
  totalSteps: number;
};

const Form = ({ items, currentStep, totalSteps }: FormProps) => {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../assets/fonts/Inter/static/Inter-Bold.ttf"),
  });

  return fontsLoaded ? (
    <KeyboardAvoidingView behavior="padding" style={styles.container}>
      <StatusBar currentStep={currentStep} totalSteps={totalSteps} />
      <View style={styles.container}>
        <View style={styles.formAreaContainer}>
          {items.map((item, index) => (
            <View style={styles.inputContainer} key={index}>
              <Text style={styles.labelText}>{item.label}</Text>
              {item.component}
            </View>
          ))}
        </View>
        <StyledButton
          style={styles.StyledButton}
          onPress={() => {}}
          buttonText={"Continue"}
        />
      </View>
    </KeyboardAvoidingView>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "flex-start",
    alignItems: "center",
    flex: 1,
    width: "95%",
    marginTop: 30,
  },
  formAreaContainer: {
    width: "100%",
    justifyContent: "flex-start",
    alignItems: "flex-start",
  },
  inputContainer: {
    width: "100%",
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    marginTop: 20,
  },
  labelText: {
    fontFamily: "Inter-Bold",
    fontSize: 23,
    color: "#fff",
  },
  StyledButton: {
    marginTop: "auto",
    marginBottom: 20,
  },
});

export default Form;
