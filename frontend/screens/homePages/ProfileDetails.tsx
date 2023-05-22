import React, { useEffect, useState } from 'react';
import {
  Text,
  View,
  StyleSheet,
  Image,
  Pressable,
  ScrollView,
  Modal,
  ActivityIndicator,
} from 'react-native';
import StyledButton from '../../components/HomePage/StyledButton';
import { Colors } from '../../constants/Colors';
import * as Font from 'expo-font';
import RemoveFriendIcon from '../../assets/removeFriend.png';
import AddFriendIcon from '../../assets/addFriend.png';
// @ts-ignore
import { useNavigation, useRoute } from '@react-navigation/native';
import { useLoginStore } from '../../stores/LoginStore';
import axiosConfig from '../../constants/axiosConfig';

const ProfileDetails = () => {
  const [loading, setLoading] = useState(false);
  const instanceToken = axiosConfig();
  const [friendStatus, setFriendStatus] = useState('');
  const route = useRoute();
  const [user, setUser] = useState({});
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });
  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  const handleFriendRequest = () => {
    if (friendStatus === 'NotFriend') {
      console.log(id);
      console.log(dbUserName);

      instanceToken
        .post(`/api/customers/${dbUserName}/friends`, id, {
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          console.log(res);
          setFriendStatus('Requested');
        })
        .catch((err) => {
          console.log(err.message);
        });
    } else if (friendStatus === 'Friend') {
      instanceToken
        .delete(`/api/customers/${dbUserName}/friends/${id}`)
        .then((res) => {
          console.log(res.data);
          setFriendStatus('NotFriend');
        })
        .catch((err) => {
          console.log(err.message);
        });
    } else if (friendStatus === 'Requested') {
      console.log(id);

      instanceToken
        .put(`/api/customers/${dbUserName}/friends/${id}/false`)
        .then((res) => {
          console.log(res.data);
          setFriendStatus('NotFriend');
        })
        .catch((err) => {
          console.log(err.message);
        });
    }
  };
  // @ts-ignore
  const { id } = route.params;
  const [fontsLoaded] = Font.useFonts({
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  useEffect(() => {
    setLoading(true);
    instanceToken.get(`/api/customers/${id}`).then((res) => {
      setUser(res.data);
      setLoading(false);
    });
  }, []);
  useEffect(() => {
    setLoading(true);
    instanceToken.get(`/api/customers/${dbUserName}/friends`).then((res) => {
      const friends = res.data;
      if (friends.filter((friend: any) => friend.username === id).length > 0) {
        setFriendStatus('Friend');
        setLoading(false);
      } else {
        instanceToken
          .get(`/api/customers/${dbUserName}/friends/outgoing_requests`)
          .then((res) => {
            const outgoingRequests = res.data;
            if (
              outgoingRequests.filter((friend: any) => friend.username === id)
                .length > 0
            ) {
              setFriendStatus('Requested');
              setLoading(false);
            } else {
              setFriendStatus('NotFriend');
              setLoading(false);
            }
          });
      }
    });
  }, []);

  return loading ? (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <ActivityIndicator size={'large'} color='#EA34C9' />
    </View>
  ) : fontsLoaded ? (
    <>
      <ScrollView>
        <View style={styles.container}>
          {
            //TODO: this will be converted into a picture component
          }
          <View style={styles.profilePicture} />
          <View style={styles.container}>
            <Text style={styles.whiteText}>
              {user.name + ' ' + user.surname}
            </Text>
            <Text style={styles.mutedText}>
              {'Ankara'}, {'Turkiye'}
            </Text>
            <Text style={styles.friendStatusText}>
              {friendStatus === 'Friend'
                ? 'Friends since 21 May 2021'
                : 'You are not friends with this user'}
            </Text>
          </View>
          <StyledButton
            buttonText={
              friendStatus === 'Friend'
                ? 'Remove Friend'
                : friendStatus === 'NotFriend'
                ? 'Send Friend Request'
                : friendStatus === ''
                ? 'Loading...'
                : 'Cancel Friend Request'
            }
            style={
              friendStatus === 'Friend'
                ? styles.buttonClose
                : friendStatus === 'NotFriend'
                ? 'Send Friend Request'
                : styles.sentRequestButton
            }
            onPress={() => handleFriendRequest()}
            imgSource={
              friendStatus === 'NotFriend' ? AddFriendIcon : RemoveFriendIcon
            }
          />
        </View>
      </ScrollView>
    </>
  ) : null;
};

const styles = StyleSheet.create({
  container: { flex: 1, alignItems: 'center' },
  profilePicture: {
    borderRadius: 40,
    width: 80,
    height: 80,
    marginTop: 25,
    backgroundColor: 'white',
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
    fontFamily: 'Inter-Regular',
  },
  friendStatusText: {
    color: Colors.gray.muted,
    fontSize: 16,
    fontFamily: 'Inter-Regular',
    marginVertical: 10,
  },
  whiteText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Inter-Bold',
    marginTop: 15,
    marginBottom: 5,
    fontWeight: 'bold',
  },
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    alignItems: 'center',
  },
  buttonClose: {
    backgroundColor: Colors.red.error,
  },
});

export default ProfileDetails;
