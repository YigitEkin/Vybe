import Grid from '@material-ui/core/Grid';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import AdminAppBar from '../components/AdminAppBar';
import AdminToolbar from '../components/AdminToolbar';
import ActivityWidget from '../widgets/ActivityWidget';
import BudgetWidget from '../widgets/BudgetWidget';
import OverviewWidget from '../widgets/OverviewWidget';
import SalesByCategoryWidget from '../widgets/SalesByCategoryWidget';
import TeamProgressWidget from '../widgets/TeamProgressWidget';
import UsersWidget from '../widgets/UsersWidget';
import { fetchData } from '../config/request';

const Dashboard = () => {
  const { t } = useTranslation();
  const userInfo = JSON.parse(localStorage.getItem('venueInfo'));
  const venueId = userInfo.venueId;

  const [checkInData, setCheckInData] = useState<number[]>([]);
  const [requestsData, setRequestsData] = useState<number[]>([]);
  const [coinsData, setCoinsData] = useState<number[]>([]);
  const [songsData, setSongsData] = useState<any[]>([]);
  const [topSongsData, setTopSongsData] = useState<any[]>([]);
  const [artistData, setArtistData] = useState<any[]>([]);

  //@ts-ignore
  useEffect(async () => {
    const data: number[] = await fetchData(`/api/venues/${venueId}/analytics/checkIns?inADayPer4Hours=true`, 'GET')
    // console.log(data);
    setCheckInData(data);
  }, []);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${venueId}/analytics/requests?inADay=true`, 'GET')
    // console.log(data);
    setRequestsData(data);
  }, []);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${venueId}/analytics/coinsSpent?inADay=true`, 'GET')
    // console.log(data);
    setCoinsData(data);
  }, []);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${venueId}/analytics/recentRequests`, 'GET')
    // console.log(data);
    setSongsData(data);
  }, []);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${venueId}/analytics/topRequests`, 'GET')
    console.log(data);
    setTopSongsData(data);
  }, []);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${venueId}/analytics/requestsPerArtist`, 'GET')
    // console.log(data);
    setArtistData(
      data.slice(0, 4).map((item: any) => {
        const [name, value] = item.split(":").map((part: any) => part.trim());
        return { name, value };
      })
    );
  }, []);

  function getTotalVisits(): string {
    return String(checkInData.reduce((a, b) => a + b, 0));
  }
  function getTotalRequests(): string {
    return String(requestsData.reduce((a, b) => a + b, 0));
  }
  function getTotalCoins(): string {
    return String(coinsData.reduce((a, b) => a + b, 0));
  }
  function getTotalSongData(): number {
    let sum = 0;
    topSongsData.forEach((item) => {
      sum += item[1];
    });
    return sum;
  }

  return (
    <React.Fragment>
      <AdminAppBar>
        <AdminToolbar title={t('dashboard.title')} />
      </AdminAppBar>
      <Grid container spacing={2}>
        <Grid item xs={12} md={4}>
          <OverviewWidget
            description={t('dashboard.overview.visits')}
            title={getTotalVisits()} />
        </Grid>
        <Grid item xs={12} md={4}>
          <OverviewWidget
            description={t('dashboard.overview.songsPlayed')}
            title={getTotalRequests()} />
        </Grid>
        <Grid item xs={12} md={4}>
          <OverviewWidget
            description={t('dashboard.overview.coinsCollected')}
            title={getTotalCoins()} />
        </Grid>
        <Grid item xs={12} md={8}>
          <ActivityWidget />
        </Grid>
        <Grid item xs={12} md={4}>
          {
            //@ts-ignore  
          }
          <BudgetWidget data={checkInData} />
        </Grid>
        <Grid item xs={12} md={12}>
          <UsersWidget data={songsData} />
        </Grid>
        <Grid item xs={12} md={8}>
          <TeamProgressWidget data={topSongsData} count={getTotalSongData()} />
        </Grid>
        <Grid item xs={12} md={4}>
          <SalesByCategoryWidget data={artistData} />
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

export default Dashboard;
