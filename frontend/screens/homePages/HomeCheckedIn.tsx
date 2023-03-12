import {
  StyleSheet,
  Text,
  View,
  FlatList,
  Pressable,
  Image,
  Alert,
} from "react-native";
import React, { useEffect, useState } from "react";
import GroupItem from "../../components/HomePage/GroupItem";
import { Colors } from "../../constants/Colors";
import { Camera, CameraType } from "expo-camera";

import FriendItem from "../../components/HomePage/FriendItem";
import FAButton from "../../components/HomePage/FAButton";
import SearchIcon from "../../assets/SearchIcon.png";
import Icon from "react-native-vector-icons/Ionicons";
import * as Font from "expo-font";
import { BarCodeScanner } from "expo-barcode-scanner";

const HomeCheckedIn = () => {
  const [isCamOpen, setIsCamOpen] = useState(false);
  const [type, setType] = useState(CameraType.back);
  const [permission, requestPermission] = Camera.useCameraPermissions();
  const [startCamera, setStartCamera] = React.useState(false);

  useEffect(() => {}, []);
  const __startCamera = async () => {
    const { status } = await Camera.requestCameraPermissionsAsync();
    //console.log(status);
    if (status === "granted") {
      setStartCamera(true);
    } else {
      Alert.alert("Please grant access to camera from device settings!");
    }
  };

  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../assets/fonts/Inter/static/Inter-Bold.ttf"),
    "Inter-Medium": require("../../assets/fonts/Inter/static/Inter-Medium.ttf"),
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });
  const data = [
    {
      text: "Deneme 1",
    },
    {
      text: "Deneme 2",
    },
    {
      text: "Deneme 3",
    },
    {
      text: "Deneme 4",
    },
    {
      text: "Deneme 5",
    },
    {
      text: "Deneme 6",
    },
    {
      text: "Deneme 7",
    },
    {
      text: "Deneme 8",
    },
  ];
  return !startCamera ? (
    <View style={styles.container}>
      <View
        style={{
          flex: 1,
          width: "100%",
        }}
      >
        <View
          style={{
            flexDirection: "row",
            marginVertical: 20,
            alignItems: "center",
          }}
        >
          <View style={styles.textContainer}>
            <Text style={styles.textStyle}>{"You are not Vybing!"}</Text>
          </View>
          <View style={{ marginLeft: "auto" }}>
            <Pressable
              style={({ pressed }) =>
                pressed ? [styles.pressed] : [styles.buttonContainer]
              }
              android_ripple={{ color: "#000" }}
            >
              <Image source={SearchIcon} />
            </Pressable>
          </View>
        </View>
        <FlatList
          data={data}
          renderItem={({ item }) => <GroupItem text={item.text} />}
          horizontal={true}
          style={{ flexGrow: 0, marginBottom: 100 }}
        />
        <Text style={[styles.textStyle, { marginBottom: 20 }]}>
          {"Friends currently Vybing"}
        </Text>
        <FriendItem />
      </View>

      <FAButton
        style={{ zIndex: 100, bottom: 100, position: "absolute" }}
        onPress={__startCamera}
      />
    </View>
  ) : (
    <View
      style={{
        flex: 1,
        width: "100%",
      }}
    >
      <View></View>
      <Camera
        style={{ flex: 1, justifyContent: "center", alignItems: "center" }}
        type={CameraType.back}
        barCodeScannerSettings={{
          barCodeTypes: [BarCodeScanner.Constants.BarCodeType.qr],
        }}
        onBarCodeScanned={(result: any) => console.log(result.data)}
      >
        <Pressable
          onPress={() => setStartCamera(false)}
          style={({ pressed }) =>
            pressed ? [styles.backButtonPressed] : [styles.backButton]
          }
        >
          <Icon name="chevron-back" color="#fff" size={40} />
        </Pressable>
        <View
          style={{
            width: 250,
            height: 250,
            borderWidth: 4,
            borderColor: "#fff",
            borderRadius: 35,
            marginBottom: 10,
          }}
        ></View>
        <Text style={styles.textStyle}>{"Scan QR"}</Text>
      </Camera>
    </View>
  );
};
const styles = StyleSheet.create({
  flatList: {
    //flex: 1,
    justifyContent: "space-between",
  },
  container: {
    flex: 1,
    width: "100%",
    alignItems: "center",
  },
  textStyle: {
    fontFamily: "Inter-Bold",
    fontSize: 24,
    color: "#fff",
    //lineHeight: 20,

    paddingLeft: 8,
  },
  textContainer: {
    flexDirection: "row",
    justifyContent: "flex-start",
  },
  buttonContainer: {
    width: 20,
    height: 20,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    justifyContent: "center",
    alignItems: "center",
    //marginTop: 20,
  },
  backButton: {
    position: "absolute",
    width: 60,
    height: 60,
    marginTop: 10,
    marginLeft: 10,
    top: 10,
    left: 10,
  },
  backButtonPressed: {
    position: "absolute",
    width: 60,
    height: 60,
    marginTop: 10,
    marginLeft: 10,
    top: 10,
    left: 10,
    opacity: 0.5,
  },
  pressed: {
    width: 20,
    height: 20,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    justifyContent: "center",
    alignItems: "center",
    //marginTop: 20,
    opacity: 0.5,
  },
});
export default HomeCheckedIn;
