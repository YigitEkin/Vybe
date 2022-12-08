import React from "react";
import { View, StyleSheet } from "react-native";
import { Colors } from "../../constants/Colors";

interface StatusBarProps {
  currentStep: number;
  totalSteps: number;
}

const StatusBar = ({ currentStep, totalSteps }: StatusBarProps) => {
  return (
    <View style={styles.container}>
      <View
        style={{
          flexBasis: `${(currentStep / totalSteps) * 100}%`,
          height: "100%",
          backgroundColor: Colors.purple.primary,
          marginHorizontal: 0,
          borderRadius: 5,
          marginTop: 20,
        }}
      />
      <View
        style={{
          flexBasis: `${((totalSteps - currentStep) / totalSteps) * 100}%`,
          height: "100%",
          backgroundColor: "#202325",
          marginHorizontal: 0,
          marginTop: 20,
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: 5,
    flexDirection: "row",
  },
});

export default StatusBar;
