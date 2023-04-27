import React, { useState } from "react";
import {
  Text,
  View,
  StyleSheet,
  Image,
  Pressable,
  ScrollView,
  Modal,
} from "react-native";
import StyledButton from "../../components/HomePage/StyledButton";
import { Colors } from "../../constants/Colors";
import * as Font from "expo-font";
// @ts-ignore
import { useNavigation } from "@react-navigation/native";
import { useLoginStore } from "../../stores/LoginStore";

const ProfileDetails = () => {
  const openModal = () => {
    setModalVisible(true);
  };
  const [notificationCount, setNotificationCount] = useState(1);
  const [modalVisible, setModalVisible] = useState(false);
  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });
  const { isLogin, setIsLogin } = useLoginStore();
  const [coinBalance, setCoinBalance] = useState(200);

  return fontsLoaded ? (
    <>
      <Modal
        animationType="fade"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}
      >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Text style={styles.modalText}>Logging Out</Text>
            <View style={{ flex: 1, justifyContent: "space-around" }}>
              <Text style={styles.modalTextStyle}>
                {"Are you sure you want to logout?"}
              </Text>
              <Pressable
                style={[styles.button, styles.buttonClose]}
                onPress={() => setIsLogin(false)}
              >
                <Text
                  style={{
                    fontFamily: "Inter-Regular",
                    color: "white",
                    fontSize: 20,
                  }}
                >
                  Yes I'm Sure
                </Text>
              </Pressable>
              <Pressable onPress={() => setModalVisible(false)}>
                <Text style={styles.modalTextStyle}>{"I changed my mind"}</Text>
              </Pressable>
            </View>
          </View>
        </View>
      </Modal>
      <ScrollView>
        <View style={styles.container}>
          {
            //TODO: this will be converted into a picture component
          }
          <View style={styles.profilePicture} />
          <StyledButton
            buttonText="Change"
            onPress={() => {
              console.log("pressed");
            }}
            style={styles.changeButton}
          />
          <View style={styles.paymentMethodsContainer}>
            <View>
              <Text style={styles.mutedText}>Payment Methods</Text>
              <Text style={styles.whiteText}>Content is Here</Text>
            </View>
            <Pressable style={styles.rightArrowContainer}>
              <Text style={styles.rightArrowText}>{">"}</Text>
            </Pressable>
          </View>
          <StyledButton
            buttonText="Logout"
            style={styles.logoutButton}
            onPress={openModal}
          />
        </View>
      </ScrollView>
    </>
  ) : null;
};

const styles = StyleSheet.create({
  sectionName: { color: "white", fontSize: 20 },
  editText: {
    color: Colors.purple.text,
    fontSize: 18,
    fontFamily: "Inter-Regular",
  },
  notificationContainer: {
    width: 30,
    height: 30,
    backgroundColor: "red",
    borderRadius: 15,
    marginTop: 0,
    justifyContent: "center",
    alignItems: "center",
    padding: 0,
    flexDirection: "row",
  },
  notificationText: {
    fontFamily: "Inter-Regular",
    fontSize: 20,
    color: "white",
    marginHorizontal: "auto",
  },
  editSectionContainer: {
    flexDirection: "row",
    borderTopColor: "#202325",
    borderWidth: 1,
    justifyContent: "space-between",
    width: "90%",
    paddingVertical: 15,
  },
  container: { flex: 1, alignItems: "center" },
  profilePicture: {
    borderRadius: 40,
    width: 80,
    height: 80,
    marginTop: 25,
    backgroundColor: "white",
  },
  logoutButton: {
    backgroundColor: Colors.red.error,
    marginBottom: 100,
  },
  changeButton: {
    backgroundColor: Colors.purple.lighter,
    marginTop: 40,
    text: { color: Colors.purple.settingsButton },
    width: "30%",
  },
  bottomSection: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "90%",
    marginTop: 60,
  },
  coinBalanceText: {
    color: "white",
    fontSize: 20,
    fontFamily: "Inter-Regular",
  },
  row_space_between_center: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  coinAmountText: {
    color: "white",
    fontSize: 20,
    fontFamily: "Inter-Regular",
    marginRight: 5,
  },
  paymentMethodsContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "90%",
    marginTop: 60,
  },
  mutedText: {
    color: Colors.gray.muted,
    fontSize: 15,
    fontFamily: "Inter-Regular",
  },
  whiteText: {
    color: "white",
    fontSize: 20,
    fontFamily: "Inter-Regular",
  },
  rightArrowContainer: {
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "column",
    marginRight: 10,
  },
  rightArrowText: {
    color: "white",
    fontSize: 20,
    fontFamily: "Inter-Regular",
  },
  centeredView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 22,
  },
  modalView: {
    margin: 20,
    width: 400,
    height: 300,
    backgroundColor: "#202325",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalTextStyle: {
    color: "#979c9e",
    fontFamily: "Inter-Regular",
    fontSize: 17,
    textAlign: "center",
  },
  modalText: {
    marginBottom: 15,
    fontFamily: "Inter-Bold",
    fontSize: 24,
    textAlign: "center",
    color: "white",
  },
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    alignItems: "center",
  },

  buttonClose: {
    backgroundColor: Colors.red.error,
  },
});

export default ProfileDetails;
