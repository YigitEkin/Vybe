import moment from "moment";
import React from "react";
import { useState } from "react";
import { View, Text, StyleSheet, Pressable, FlatList } from "react-native";
import * as Font from "expo-font";
import { Colors } from "../../../constants/Colors";

interface NotificationCardProps {
  id: number;
  user: string;
  description: string;
  time: Date;
}

function NotificationCard(data: NotificationCardProps) {
  const [fontsLoaded] = Font.useFonts({
    "Inter-Bold": require("../../../assets/fonts/Inter/static/Inter-Bold.ttf"),
    "Inter-Regular": require("../../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });

  return fontsLoaded ? (
    <View style={styles.container}>
      <View style={styles.basis80}>
        <View style={styles.row_start_center}>
          <View style={styles.picture} />
          <View style={styles.ml_20}>
            <Text style={styles.username}>{data.user}</Text>
            <Text style={styles.desc}>{data.description}</Text>
          </View>
        </View>
        <View style={styles.time}>
          <Text style={styles.timeText}>
            {Math.abs(moment(data.time).diff(moment(), "minutes")) +
              " minutes ago"}
          </Text>
        </View>
      </View>
      <View style={styles.btnContainer}>
        <Pressable style={styles.acceptBtn}>
          <Text style={styles.acceptText}>Accept</Text>
        </Pressable>
        <Pressable style={styles.declineBtn}>
          <Text style={styles.declineText}>Decline</Text>
        </Pressable>
      </View>
    </View>
  ) : null;
}

const Notifications = () => {
  const [notifications, setNotifications] = useState<NotificationCardProps[]>([
    {
      id: 1,
      user: "Mehmet Berk Türkçapar",
      description: "Added you as a friend",
      time: new Date(),
    },
    {
      id: 2,
      user: "Mehmet Berk Türkçapar",
      description: "Added you as a friend",
      time: new Date(),
    },
    {
      id: 3,
      user: "Mehmet Berk Türkçapar",
      description: "Added you as a friend",
      time: new Date(),
    },
    {
      id: 4,
      user: "Mehmet Berk Türkçapar",
      description: "Added you as a friend",
      time: new Date(),
    },
  ]);

  return (
    <FlatList
      data={notifications}
      renderItem={({ item }) => <NotificationCard {...item} />}
      keyExtractor={(item) => item.id.toString()}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
    padding: 20,
    borderRadius: 10,
    margin: 10,
    shadowColor: "#000",
    flexDirection: "row",
  },
  basis80: { flexBasis: "80%" },
  basis20: { flexBasis: "20%" },
  row_start_center: {
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
  },
  picture: {
    backgroundColor: "black",
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  ml_20: { marginLeft: 20 },
  username: {
    color: "black",
    fontWeight: "bold",
    fontSize: 18,
    fontFamily: "Inter-Bold",
  },
  desc: { fontFamily: "Inter-Light" },
  time: {
    flexDirection: "row",
    justifyContent: "flex-start",
    alignItems: "center",
    paddingTop: 15,
    marginLeft: "20%",
  },
  timeText: { fontFamily: "Inter-Regular", color: Colors.gray.muted },
  btnContainer: {
    flexBasis: "20%",
    justifyContent: "space-between",
    alignItems: "center",
  },
  acceptBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: "#888BF4",
  },
  acceptText: { color: "#888BF4" },
  declineBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: "red",
    marginTop: 10,
  },
  declineText: { color: "red" },
});

export default Notifications;
