import React, { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  TextInput,
  Image,
  Pressable,
  Modal,
  FlatList,
} from "react-native";
import { formatPhoneNumber } from "./utils/helpers";
import { data } from "./countryData/data";
import arrowDown from "../../assets/arrowDown.png";
import StyledButton from "../HomePage/StyledButton";
import { DismissKeyboard } from "../common/DismissKeyboard";
import * as Font from "expo-font";

function PhoneCodeModal({
  data,
  setSelectedCallingCode,
  setModalVisible,
  onChange,
  modalPlaceholderText,
  modalHeaderText,
  style,
}) {
  const [searchText, setSearchText] = useState("");
  const filteredData = data.filter((item) => {
    const itemData = item.name.toUpperCase();
    const textData = searchText.toUpperCase();
    return itemData.indexOf(textData) > -1;
  });

  const styles = style || defaultStyles;
  return (
    <View style={styles.modalContainer}>
      <View style={styles.modalHeader}>
        <Text style={styles.modalHeaderText}>
          {modalHeaderText || "Select your country"}
        </Text>
      </View>
      <View style={styles.modalBody}>
        <View style={styles.searchBarContainer}>
          <TextInput
            style={styles.searchBar}
            placeholder={modalPlaceholderText || "Search"}
            placeholderTextColor={"#979c9e"}
            onChangeText={(text) => setSearchText(text)}
          />
        </View>
        <View style={styles.countryListContainer}>
          <FlatList
            data={filteredData}
            renderItem={({ item }) => (
              <Pressable
                style={styles.countryItem}
                onPress={() => {
                  setSelectedCallingCode(item);
                  setModalVisible(false);
                  onChange && onChange(item);
                }}
              >
                <Text style={styles.countryItemFlag}>{item.flag}</Text>
                <Text style={styles.countryItemName}>{item.name}</Text>
                <Text style={styles.countryItemCode}>{item.dial_code}</Text>
              </Pressable>
            )}
            keyExtractor={(item) => item.name}
          />
        </View>
      </View>
    </View>
  );
}

const EnterPhoneNumber = ({
  headerText,
  modalHeaderText,
  subHeaderText,
  modalPlaceholderText,
  onChange,
  setPhoneNumber,
  phoneNumber,
  phoneNumberFormat,
  style,
  buttonText,
  mutedText,
  onPress,
  selectedCallingCode,
  setSelectedCallingCode,
}) => {
  const [isModalVisible, setModalVisible] = useState(false);

  const styles = style || defaultStyles;

  const textInputValue = phoneNumberFormat
    ? phoneNumberFormat(phoneNumber)
    : formatPhoneNumber(phoneNumber);

  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../assets/fonts/Inter/static/Inter-Bold.ttf"),
    "Inter-Medium": require("../../assets/fonts/Inter/static/Inter-Medium.ttf"),
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });

  return fontsLoaded ? (
    <DismissKeyboard>
      <KeyboardAvoidingView behavior="padding" style={styles.container}>
        <View style={styles.container}>
          <View style={styles.formAreaContainer}>
            <View style={styles.inputContainer}>
              <Text style={styles.headerText}>{headerText}</Text>
              <Text style={styles.subHeaderText}>{subHeaderText}</Text>
            </View>
            <View style={styles.inputContainer}>
              <View style={styles.inputWrapper}>
                <Pressable
                  style={styles.picker}
                  onPress={() => setModalVisible(true)}
                >
                  <Text style={styles.pickerText}>
                    {selectedCallingCode.flag}
                  </Text>
                  <Text style={styles.pickerText}>
                    {selectedCallingCode.dial_code}
                  </Text>
                  <Image source={arrowDown} />
                </Pressable>
                <TextInput
                  value={textInputValue}
                  style={styles.input}
                  keyboardType="phone-pad"
                  onChangeText={(e) => setPhoneNumber(e)}
                  maxLength={14}
                />
              </View>
            </View>
            <View style={styles.inputContainer}>
              <Text style={styles.textMuted}>{mutedText}</Text>
            </View>
          </View>
        </View>
        <Modal
          visible={isModalVisible}
          presentationStyle="pageSheet"
          animationType="slide"
        >
          <PhoneCodeModal
            data={data}
            setSelectedCallingCode={setSelectedCallingCode}
            selectedCallingCode={selectedCallingCode}
            setModalVisible={setModalVisible}
            onChange={onChange}
            modalPlaceholderText={modalPlaceholderText}
            modalHeaderText={modalHeaderText}
          />
        </Modal>
        <StyledButton
          style={{ marginTop: "auto", marginBottom: 20 }}
          onPress={onPress}
          buttonText={buttonText}
        />
      </KeyboardAvoidingView>
    </DismissKeyboard>
  ) : null;
};

const defaultStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#000",
    justifyContent: "flex-start",
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
    fontSize: 20,
    color: "#fff",
    flexBasis: "75%",
    marginLeft: "auto",
  },
  textMuted: {
    fontSize: 13,
    color: "#979c9e",
  },
  inputWrapper: {
    width: "100%",
    height: 50,
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    borderRadius: 10,
    borderWidth: 3,
    borderColor: "#964fd0",
  },
  picker: {
    width: "100%",
    height: "100%",
    flexBasis: "25%",
    marginRight: "auto",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 10,
  },
  pickerText: {
    fontSize: 17,
    color: "#fff",
  },
  modalContainer: {
    flex: 1,
    backgroundColor: "#000",
  },
  modalHeader: {
    width: "100%",
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    borderBottomWidth: 1,
    borderBottomColor: "#979c9e",
  },

  modalHeaderText: {
    fontSize: 20,
    color: "#fff",
  },
  modalBody: {
    flex: 1,
    width: "100%",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    marginTop: 20,
  },
  searchBarContainer: {
    width: "100%",
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    borderBottomWidth: 1,
    borderBottomColor: "#979c9e",
    paddingBottom: 10,
  },
  searchBar: {
    width: "90%",
    height: "100%",
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    borderRadius: 10,
    borderWidth: 3,
    borderColor: "964fd0",
    color: "#fff",
  },
  searchBarInput: {
    width: "100%",
    height: "100%",
    paddingLeft: 20,
    borderRadius: 5,
    fontSize: 20,
    color: "#fff",
    flexBasis: "75%",
    marginLeft: "auto",
    padding: 5,
  },
  countryListContainer: {
    flex: 1,
    width: "100%",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    marginTop: 20,
  },
  countryItem: {
    width: "100%",
    height: 50,
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    borderBottomWidth: 1,
    borderBottomColor: "#979c9e",
  },
  countryItemText: {
    fontSize: 20,
    color: "#fff",
    marginLeft: 20,
  },
  countryItemFlag: {
    width: 30,
    height: 30,
    marginLeft: 20,
  },

  countryItemDialCode: {
    fontSize: 20,
    color: "#fff",
    marginLeft: "auto",
    marginRight: 20,
  },

  countryItemName: {
    fontSize: 20,
    color: "#fff",
    marginLeft: 20,
  },

  countryItemCode: {
    fontSize: 20,
    color: "#fff",
    marginLeft: "auto",
    marginRight: 20,
  },
});

export default EnterPhoneNumber;
