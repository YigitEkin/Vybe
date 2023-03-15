import React, { useState } from "react";
import HomeCheckedIn from "./HomeCheckedIn";
import HomeNotCheckedIn from "./HomeNotCheckedIn";

const HomePageWrapper = () => {
  const [isUserCheckedInVenue, setIsUserCheckedInVenue] = useState(false);

  return isUserCheckedInVenue ? <HomeCheckedIn /> : <HomeNotCheckedIn />;
};

export default HomePageWrapper;
