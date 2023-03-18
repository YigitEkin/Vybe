import React, { useState } from "react";
import {
  Text,
  View,
  StyleSheet,
  Image,
  Pressable,
  ScrollView,
} from "react-native";
import StyledButton from "../../../components/HomePage/StyledButton";
import { Colors } from "../../../constants/Colors";
import * as Font from "expo-font";
// @ts-ignore
import CoinIcon from "../../../assets/coin.png";
import { useNavigation } from "@react-navigation/native";

type editSectionArea = {
  name: string;
  editability: boolean;
  borderBottomless?: boolean;
  marginTop?: number;
  notificationCount?: number;
  onPress?: () => void;
};

const editSections: editSectionArea[] = [
  {
    name: "First Name",
    editability: true,
    marginTop: 20,
  },
  {
    name: "Last Name",
    editability: true,
  },
  {
    name: "Location",
    editability: true,
  },
  {
    name: "Notfications",
    editability: false,
    borderBottomless: true,
    notificationCount: 0,
    onPress: () => {},
  },
];

const EditProfileSection = (
  section: editSectionArea,
  notificationCount: number
) => {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });
  const navigation = useNavigation();

  return fontsLoaded ? (
    <View
      style={[
        styles.editSectionContainer,
        {
          borderBottomColor: section.borderBottomless ? undefined : "#202325",
          marginTop: section.marginTop,
        },
      ]}
    >
      <Text style={styles.sectionName}>{section.name}</Text>
      {section.editability ? (
        <Pressable>
          <Text style={styles.editText}>Edit</Text>
        </Pressable>
      ) : section.notificationCount ? (
        <Pressable
          style={styles.notificationContainer}
          onPress={() => navigation.navigate("Notifications" as never)}
        >
          <Text style={styles.notificationText}>
            {section.notificationCount}
          </Text>
        </Pressable>
      ) : null}
    </View>
  ) : null;
};

const SettingsPage = () => {
  const [notificationCount, setNotificationCount] = useState(1);
  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });
  const [coinBalance, setCoinBalance] = useState(200);

  return fontsLoaded ? (
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
        {editSections.map((section) => (
          <EditProfileSection
            {...section}
            key={section.name}
            notificationCount={notificationCount}
          />
        ))}
        <View style={styles.bottomSection}>
          <Text style={styles.coinBalanceText}>Coin Balance</Text>
          <View style={styles.row_space_between_center}>
            <Text style={styles.coinAmountText}>{coinBalance}</Text>
            <Image source={CoinIcon} />
          </View>
        </View>

        <View style={styles.paymentMethodsContainer}>
          <View>
            <Text style={styles.mutedText}>Payment Methods</Text>
            <Text style={styles.whiteText}>Content is Here</Text>
          </View>
          <Pressable style={styles.rightArrowContainer}>
            <Text style={styles.rightArrowText}>{">"}</Text>
          </Pressable>
        </View>
      </View>
    </ScrollView>
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
});

export default SettingsPage;
