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
import RemoveFriendIcon from '../../assets/removeFriend.png';
import AddFriendIcon from '../../assets/addFriend.png';
// @ts-ignore
import { useNavigation, useRoute } from "@react-navigation/native";
import { useLoginStore } from "../../stores/LoginStore";

const ProfileDetails = () => {
  const [friendStatus, setFriendStatus] = useState("Friend");
  const route = useRoute();
  const [user, setUser] = useState({
    id: 1,
    name: "John Doe",
    city: "Ankara",
    country: "Turkey"
  });
  const handleFriendRequest = () => {
    if (friendStatus === "NotFriend") {
      setFriendStatus("Requested");
    } else if (friendStatus === "Friend") {
      setFriendStatus("NotFriend");
    } else if (friendStatus === "Requested") {
      setFriendStatus("NotFriend");
    }
  };
  // @ts-ignore
  const { id } = route.params;
  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../assets/fonts/Inter/static/Inter-Regular.ttf"),
  });

  return fontsLoaded ? (
    <>
      <ScrollView>
        <View style={styles.container}>
          {
            //TODO: this will be converted into a picture component
          }
          <View style={styles.profilePicture} />
          <View style={styles.container}>
            <Text style={styles.whiteText}>{user.name} {id}</Text>
            <Text style={styles.mutedText}>{user.city}, {user.country}</Text>
            <Text style={styles.friendStatusText}>
              {(friendStatus === "Friend") ? (
                "Friends since 21 May 2021"
              ) : (
                "You are not friends with this user"
              )}
            </Text>
          </View>
          <StyledButton
            buttonText={
              friendStatus === "Friend" ? (
                "Remove Friend"
              ) : (
                friendStatus === "NotFriend" ? (
                  "Send Friend Request"
                ) : (
                  "Cancel Friend Request"
                )
              )
            }
            style={
              friendStatus === "Friend" ? (
                styles.buttonClose
              ) : (
                friendStatus === "NotFriend" ? (
                  "Send Friend Request"
                ) : (
                  styles.sentRequestButton
                )
              )
            }
            onPress={() => handleFriendRequest()}
            imgSource={
              friendStatus === "NotFriend" ? (
                AddFriendIcon
              ) : (
                RemoveFriendIcon
              )
            }
          />
        </View>
      </ScrollView>
    </>
  ) : null;
};

const styles = StyleSheet.create({
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
    marginBottom: 10,
  },
  sentRequestButton: {
    backgroundColor: Colors.purple.lighter,
    text: { color: Colors.purple.settingsButton },
  },
  changeButton: {
    backgroundColor: Colors.purple.lighter,
    text: { color: Colors.purple.settingsButton },
  },
  mutedText: {
    color: Colors.gray.muted,
    fontSize: 15,
    fontFamily: "Inter-Regular",
  },
  friendStatusText: {
    color: Colors.gray.muted,
    fontSize: 16,
    fontFamily: "Inter-Regular",
    marginVertical: 10
  },
  whiteText: {
    color: "white",
    fontSize: 20,
    fontFamily: "Inter-Bold",
    marginTop: 15,
    marginBottom: 5,
    fontWeight: 'bold',
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
