import * as React from "react";
import { useState } from "react";
import { NavigationContainer } from "@react-navigation/native";
import SignUpMail from "../screens/signup/SignUpMail";
import SignUpCompletedScreen from "../screens/signup/SignUpCompletedScreen";
import SignUpPassword from "../screens/signup/SignUpPassword";
import SignUpUsername from "../screens/signup/SignupUsername";
import EnterPhoneNumber from "../components/2fa/EnterPhoneNumber";
import HomePage from "../screens/HomePage";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import EnterPhoneNumberSignUp from "../screens/signup/EnterPhoneNumberSignUp";
import EnterPhoneNumberLogin from "../screens/login/EnterPhoneNumberLogin";
import LoginVerification from "../screens/Verification/LoginVerification";
import SignupVerification from "../screens/Verification/SignupVerification";
import HomeNotCheckedIn from "../screens/homePages/HomeNotCheckedIn";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { SafeAreaView } from "react-native-safe-area-context";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import Icon from "react-native-vector-icons/EvilIcons";
import SettingsPage from "../screens/homePages/Settings";
import MapPage from "../screens/homePages/Map";
import HomePageWrapper from "../screens/homePages/HomePageWrapper";

//const Tab = createBottomTabNavigator();

//export const BottomTab = () => {
//  return (
//    <Tab.Navigator>
//      <Tab.Screen name='Home' component={HomeNotCheckedIn} />
//    </Tab.Navigator>
//  );
//};

type Route = {
  name: string;
  component: any;
  screenOptions?: any;
  icon?: any;
};

const NotLoggedInRoutes: Route[] = [
  {
    name: "SignUpMail",
    component: SignUpMail,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "HomePage",
    component: HomePage,
    screenOptions: {
      headerShown: false,
    },
  },
  {
    name: "SignUpPassword",
    component: SignUpPassword,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "SignUpUsername",
    component: SignUpUsername,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "LoginVerification",
    component: LoginVerification,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "SignupVerification",
    component: SignupVerification,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "SignUpCompletedScreen",
    component: SignUpCompletedScreen,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "EnterPhoneNumberLogin",
    component: EnterPhoneNumberLogin,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  {
    name: "EnterPhoneNumberSignUp",
    component: EnterPhoneNumberSignUp,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
  },
  //TODO: Seperate this from not logged in routes !!!
];
const loggedInRoutes: Route[] = [
  {
    name: "HomePage",
    component: HomePageWrapper,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
    icon: ({ color, size }: any) => (
      <MaterialCommunityIcons name="home-outline" color={color} size={size} />
    ),
  },
  {
    name: "Map",
    component: MapPage,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
    icon: ({ color, size }: any) => (
      <Icon name="location" color={color} size={size} />
    ),
  },
  {
    name: "Settings",
    component: SettingsPage,
    screenOptions: {
      headerShown: true,
      headerBackTitleVisible: false,
      headerTintColor: "#fff",
      headerTitle: "",
      headerStyle: {
        backgroundColor: "#000",
        shadowColor: "transparent",
        height: 0,
      },
    },
    icon: ({ color, size }: any) => (
      <Icon name="user" color={color} size={size} />
    ),
  },
];

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

export default function Router() {
  const [loggedIn, setLoggedIn] = useState(true);
  return !loggedIn ? (
    <>
      <SafeAreaView
        style={{ backgroundColor: "#000", flex: 1, marginBottom: 50 }}
      >
        <NavigationContainer>
          <Stack.Navigator
            initialRouteName="HomePage"
            screenOptions={{
              contentStyle: {
                backgroundColor: "#000",
                width: "100%",
              },
            }}
          >
            {NotLoggedInRoutes.map((route, index) => (
              <Stack.Screen
                options={{
                  ...route.screenOptions,
                  contentStyle: {
                    backgroundColor: "#000",
                    width: "100%",
                    alignItems: "center",
                  },
                }}
                key={index}
                name={route.name}
                component={route.component}
              />
            ))}
          </Stack.Navigator>
        </NavigationContainer>
      </SafeAreaView>
    </>
  ) : (
    <>
      <SafeAreaView style={{ flex: 1, backgroundColor: "#202325" }}>
        <NavigationContainer>
          <Tab.Navigator
            sceneContainerStyle={{ backgroundColor: "#000" }}
            screenOptions={{
              headerShown: false,
              tabBarActiveTintColor: "#fff",
              tabBarInactiveTintColor: "#6C7072",
              tabBarShowLabel: false,

              tabBarItemStyle: {
                backgroundColor: "#202325",
                margin: 0,
                padding: 0,
              },
              tabBarStyle: {
                backgroundColor: "#202325",
                position: "absolute",

                margin: 0,
              },
            }}
          >
            {loggedInRoutes.map((route, index) => (
              <Tab.Screen
                options={{
                  ...route.screenOptions,
                  tabBarIcon: route.icon,
                }}
                key={index}
                name={route.name}
                component={route.component}
              />
            ))}
          </Tab.Navigator>
        </NavigationContainer>
      </SafeAreaView>
    </>
  );
}
