import { StyleSheet, Text, View, ImageBackground } from "react-native";
import React, { useEffect, useState } from "react";
import * as Font from "expo-font";

const GroupItem = ({ text }: any) => {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../assets/fonts/Inter/static/Inter-Bold.ttf"),
    "Inter-Medium": require("../../assets/fonts/Inter/static/Inter-Medium.ttf"),
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });
  //  const image = { uri: '../../assets/GroupItemImg.png' };
  return (
    fontsLoaded && (
      <View style={styles.container}>
        <View style={styles.itemContainer}>
          {/*<ImageBackground source={image} style={styles.imageStyle} />*/}
        </View>
        <Text style={styles.textStyle}>{text}</Text>
      </View>
    )
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  itemContainer: {
    height: 88,
    width: 88,
    borderRadius: 16,
    backgroundColor: "#fff",
    marginBottom: 10,
    marginHorizontal: 10,
  },
  textStyle: {
    fontFamily: "Inter-Bold",
    fontSize: 14,
    color: "#fff",
    lineHeight: 20,
  },
  imageStyle: {
    flex: 1,
    resizeMode: "cover",
    justifyContent: "center",
  },
});
export default GroupItem;
