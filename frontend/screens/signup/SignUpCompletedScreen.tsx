import React, { useMemo } from "react";
import { Image, StyleSheet, View } from "react-native";
import Form, { FormItem } from "../../components/Form/Form";
// @ts-ignore
import Logo from "../../assets/Tick.png";
import { useSignUpStore } from "../../stores/SignUpStore";

const SignUpCompletedScreen = ({ navigation }: any) => {
  const { setEmail, setPassword, setUsername, setPhoneNumber } = useSignUpStore(
    (state: any) => {
      return {
        setEmail: state.setEmail,
        setPassword: state.setPassword,
        setUsername: state.setUsername,
        setPhoneNumber: state.setPhoneNumber,
      };
    }
  );
  const formItems: FormItem[] = useMemo(
    () => [
      {
        label: "You Are All Done!",
        wrapperStyle: styles.centerText,
        labelStyle: styles.labelTextForForm,
        component: (
          <Image
            source={Logo}
            style={{ marginHorizontal: "22.5%", marginTop: 50 }}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = "HomePage" as never;
  return (
    <Form
      items={formItems}
      currentStep={4}
      totalSteps={4}
      navigateRoute={navigateRoute}
      validator={() => true}
      cb={() => {
        setEmail(null);
        setPassword(null);
        setUsername(null);
        setPhoneNumber(null);
        navigation.navigate(navigateRoute);
      }}
    />
  );
};

const styles = StyleSheet.create({
  textInput: {
    width: "100%",
    height: 50,
    borderWidth: 2,
    borderColor: "#444",
    borderRadius: 5,
    color: "#fff",
    fontSize: 20,
    paddingLeft: 10,
    paddingRight: 10,
    marginTop: 20,
  },
  labelText: {
    marginHorizontal: 5,
    fontSize: 30,
  },
  centerText: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
  },
  labelTextForForm: {
    fontFamily: "Inter-Bold",
    fontSize: 30,
    color: "#fff",
  },
});

export default SignUpCompletedScreen;
