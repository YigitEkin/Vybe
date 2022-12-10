import * as React from "react";
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

type Route = {
  name: string;
  component: any;
  screenOptions?: any;
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
];

const Stack = createNativeStackNavigator();

export default function Router() {
  return (
    <>
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
    </>
  );
}
