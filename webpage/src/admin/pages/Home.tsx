import Grid from '@material-ui/core/Grid';
import React, { useState, useEffect } from 'react';
import AdminAppBar from '../components/AdminAppBar';
import AdminToolbar from '../components/AdminToolbar';
import RecentNotifications from '../components/RecentNotifications';
import AchievementWidget from '../widgets/AchievementWidget';
import FollowersWidget from '../widgets/FollowersWidget';
import MeetingWidgets from '../widgets/MeetingWidgets';
import PersonalTargetsWidget from '../widgets/PersonalTargetsWidget';
import ViewsWidget from '../widgets/ViewsWidget';
import WelcomeWidget from '../widgets/WelcomeWidget';
import { fetchData } from '../config/request';

const Home = () => {
  const venueId = 2;
  const [checkInData, setCheckInData] = useState<number[]>([]);
  // @ts-ignore
  useEffect(async () => {
    const data: number[] = await fetchData(`/api/venues/${venueId}/analytics/checkIns?inAYear=true`, 'GET')
    console.log(data);
    setCheckInData(data);
  }, []);
  return (
    <React.Fragment>
      <AdminAppBar>
        <AdminToolbar>{/*<RecentNotifications />*/}</AdminToolbar>
      </AdminAppBar>
      <Grid container spacing={2}>
        <Grid item xs={12} md={4}>
          <WelcomeWidget />
          {/*<AchievementWidget />*/}
        </Grid>
        <Grid item xs={12} md={8}>
          <ViewsWidget data={checkInData} />
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

export default Home;
