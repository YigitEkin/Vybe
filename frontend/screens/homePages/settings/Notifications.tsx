import moment from 'moment';
import React, { useEffect } from 'react';
import { useState } from 'react';
import { View, Text, StyleSheet, Pressable, FlatList } from 'react-native';
import * as Font from 'expo-font';
import { Colors } from '../../../constants/Colors';
import { useLoginStore } from '../../../stores/LoginStore';
import axiosConfig from '../../../constants/axiosConfig';

export interface NotificationCardProps {
  id: number;
  user: string;
  username: string;
  description: string;
  time: Date;
}

function NotificationCard(
  data: NotificationCardProps,
  { setNotification }: any
) {
  const instanceToken = axiosConfig();
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });
  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  const handleRequest = (isAccept: string) => {
    console.log(data.username);
    instanceToken
      .put(`/api/customers/${data.username}/friends/${dbUserName}/${isAccept}`)
      .then((res) => {
        console.log(res.data);
        setNotification();
      })
      .catch((err) => {
        console.log(err.message);
      });
  };
  return fontsLoaded ? (
    <View style={styles.container}>
      <View style={styles.basis80}>
        <View style={styles.row_start_center}>
          <View style={styles.picture} />
          <View style={styles.ml_20}>
            <Text style={styles.username}>
              {data.name} {data.surname}
            </Text>
            <Text style={styles.desc}>{`Sent you a friend request`}</Text>
          </View>
        </View>
        {/*<View style={styles.time}>
          <Text style={styles.timeText}>
            {Math.abs(moment(data.time).diff(moment(), 'minutes')) +
              ' minutes ago'}
          </Text>
        </View>*/}
      </View>
      <View style={styles.btnContainer}>
        <Pressable
          style={styles.acceptBtn}
          onPress={() => handleRequest('true')}
        >
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
  const [notifications, setNotifications] = useState<NotificationCardProps[]>(
    []
  );
  const instanceToken = axiosConfig();
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });
  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  function refetchData() {
    console.log('refetching');
    instanceToken
      .get(`/api/customers/${dbUserName}/friends/incoming_requests`)
      .then((res) => {
        //console.log(res.data, res.data.length);
        setNotifications(res.data);
      });
  }
  useEffect(() => {
    instanceToken
      .get(`/api/customers/${dbUserName}/friends/incoming_requests`)
      .then((res) => {
        //console.log(res.data, res.data.length);
        setNotifications(res.data);
      });
  }, []);

  return (
    <FlatList
      data={notifications}
      renderItem={({ item }) => (
        <NotificationCard {...item} setNotification={refetchData} />
      )}
      //keyExtractor={(item) => item.id.toString()}
    />
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    margin: 10,
    shadowColor: '#000',
    flexDirection: 'row',
  },
  basis80: { flexBasis: '70%' },
  basis20: { flexBasis: '20%' },
  row_start_center: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  picture: {
    backgroundColor: 'black',
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  ml_20: { marginLeft: 20 },
  username: {
    color: 'black',
    fontWeight: 'bold',
    fontSize: 18,
    fontFamily: 'Inter-Bold',
  },
  desc: { fontFamily: 'Inter-Light' },
  time: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingTop: 15,
    marginLeft: '20%',
  },
  timeText: { fontFamily: 'Inter-Regular', color: Colors.gray.muted },
  btnContainer: {
    flexBasis: '30%',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  acceptBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: '#888BF4',
  },
  acceptText: { color: '#888BF4' },
  declineBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: 'red',
    marginTop: 10,
  },
  declineText: { color: 'red' },
});

export default Notifications;
