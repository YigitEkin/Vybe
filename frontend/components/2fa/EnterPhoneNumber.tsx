import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  TextInput,
  Image,
  ScrollView,
} from "react-native";
import * as Font from "expo-font";
import StyledButton from "../HomePage/StyledButton";
import { Colors } from "../../constants/Colors";
import {
  getCountryFlag,
  NationsPhoneData,
} from "../../constants/NationsPhoneData";
import { AntDesign } from "@expo/vector-icons";

type EnterPhoneNumberProps = {
  headerText: string;
  subHeaderText: string;
  buttonText: string;
  onPress: () => void;
  onChangeText: (text: string) => void;
  value: any;
};

type PickerDisplayProps = {
  text: string;
  image: any;
};

function PickerDisplay({ text, image }: PickerDisplayProps) {
  return (
    <View style={styles.picker}>
      <Image source={image} resizeMethod="scale" resizeMode="contain" />
      <Text style={styles.pickerText}>{text}</Text>
      <AntDesign name="caretdown" size={24} color="black" />
    </View>
  );
}

const EnterPhoneNumber = ({
  headerText,
  subHeaderText,
  buttonText,
  onPress,
  onChangeText,
  value,
}: EnterPhoneNumberProps) => {
  const [selectedCallingCode, setSelectedCallingCode] = useState<string>("+1");
  const [image, setImage] = useState<any>(() => {
    return getCountryFlag(selectedCallingCode);
  });

  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../assets/fonts/Inter/static/Inter-Bold.ttf"),
    "Inter-Medium": require("../../assets/fonts/Inter/static/Inter-Medium.ttf"),
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });

  return fontsLoaded ? (
    <KeyboardAvoidingView behavior="padding" style={styles.container}>
      <View style={styles.container}>
        <View style={styles.formAreaContainer}>
          <View style={styles.inputContainer}>
            <Text style={styles.headerText}>{headerText + "."}</Text>
            <Text style={styles.subHeaderText}>{subHeaderText}</Text>
          </View>
          <View style={styles.inputContainer}>
            <View style={styles.inputWrapper}>
              <TextInput
                value={value}
                style={styles.input}
                keyboardType="phone-pad"
                onChangeText={onChangeText}
              />
            </View>
          </View>
          <View style={styles.inputContainer}>
            <Text style={styles.textMuted}>
              You will receive an SMS verification that may apply message and
              data rates.
            </Text>
          </View>
        </View>
        <StyledButton
          style={styles.StyledButton}
          onPress={onPress}
          buttonText={buttonText}
        />
      </View>
    </KeyboardAvoidingView>
  ) : (
    <></>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#000",
    justifyContent: "center",
    alignItems: "center",
    width: "95%",
  },
  formAreaContainer: {
    width: "100%",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    marginTop: "30%",
  },
  inputContainer: {
    width: "100%",
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    marginTop: 20,
  },
  headerText: {
    fontFamily: "Inter-Bold",
    fontSize: 40,
    color: "#fff",
  },
  subHeaderText: {
    marginTop: 5,
    fontFamily: "Inter-Regular",
    fontSize: 20,
    color: "#fff",
  },
  StyledButton: {
    marginTop: "auto",
    marginBottom: 20,
  },
  input: {
    width: "100%",
    height: "100%",
    paddingLeft: 20,
    borderRadius: 5,
    fontFamily: "Inter-Medium",
    fontSize: 20,
    color: "#fff",
    flexBasis: "80%",
    marginLeft: "auto",
  },
  textMuted: {
    fontFamily: "Inter-Regular",
    fontSize: 13,
    color: Colors.gray.muted,
  },
  inputWrapper: {
    width: "100%",
    height: 50,
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    borderRadius: 10,
    borderWidth: 3,
    borderColor: Colors.purple.primary,
  },
  picker: {
    width: "100%",
    height: "100%",
    borderRadius: 5,
    flexBasis: "20%",
    backgroundColor: Colors.purple.primary,
    marginRight: "auto",
    flexDirection: "row",
  },
  pickerText: {
    fontSize: 20,
    color: "#fff",
    fontFamily: "Inter-Medium",
  },
});

export default EnterPhoneNumber;
