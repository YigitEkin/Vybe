// @ts-nocheck
import Avatar from '@material-ui/core/Avatar';
import Box from '@material-ui/core/Box';
import Fab from '@material-ui/core/Fab';
import Grid from '@material-ui/core/Grid';
import { Button } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import PersonIcon from '@material-ui/icons/Person';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../../auth/contexts/AuthProvider';
import QueryWrapper from '../../core/components/QueryWrapper';
import { useSnackbar } from '../../core/contexts/SnackbarProvider';
import AdminAppBar from '../components/AdminAppBar';
import AdminToolbar from '../components/AdminToolbar';
import QRCode from 'react-qr-code';
import { fetchData } from '../config/request';

const profileMenuItems = [
  {
    key: 'profile.menu.info',
    path: './information',
  },
  {
    key: 'profile.menu.password',
    path: './password',
  },
];

const Profile = () => {
  const { isLoggingOut, logout } = useAuth();
  const userInfo = JSON.parse(localStorage.getItem('venueInfo'));
  const [username, setUsername] = useState(localStorage.getItem('username'));
  const snackbar = useSnackbar();
  const { t } = useTranslation();
  const [base64, setBase64] = useState('')

  const handleLogout = () => {
    logout().catch(() =>
      snackbar.error(t('common.errors.unexpected.subTitle'))
    );
  };

  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/${userInfo.venueId}/images`, 'GET')
    if (data === null || data.length === 0) {
      setBase64('')
      return
    }
    console.log(data[0].image);
    setBase64(data[0].image)
  }, []);

  const uploadImage = async (base64ToUpload: String) => {
    // console.log(base64ToUpload);
    const data = await fetchData(`/api/venues/${userInfo.venueId}/images`, 'POST', {
      image: String(base64ToUpload),
      id: userInfo.venueId,
    });
    console.log(data);
    if (data) {
      setBase64(base64ToUpload)
    }
    else {
      snackbar.error(t('common.errors.unexpected.subTitle'))
    }
  };

  return (
    <React.Fragment>
      <AdminAppBar>
        <AdminToolbar>
          <Fab
            aria-label='logout'
            color='secondary'
            disabled={isLoggingOut}
            onClick={handleLogout}
          >
            <ExitToAppIcon />
          </Fab>
        </AdminToolbar>
      </AdminAppBar>
      <Grid container spacing={12}>
        <Grid item xs={12} md={12} marginTop={3}>
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              textAlign: 'center',
              mb: 6,
            }}
          >
            <Avatar
              sx={{
                bgcolor: 'background.paper',
                mb: 3,
                height: 220,
                width: 220,
              }}
            >
              {(base64 === '' || base64 === null) ? (
                <PersonIcon
                  sx={{
                    fontSize: 160,
                  }} />
              ) : (
                <img
                  width={'auto'}
                  height={220}
                  src={`${base64}`}
                />
              )}
            </Avatar>

            <Typography
              component='div'
              variant='h4'
            >{`${userInfo?.venueName} `}</Typography>
            <label htmlFor="upload-photo">
              <input
                style={{ display: 'none' }}
                id="upload-photo"
                name="upload-photo"
                type="file"
                accept="image/*"
                onChange={(e) => {
                  const file = e.target.files[0];
                  if (!file) return;
                  const reader = new FileReader();
                  reader.readAsDataURL(file);
                  reader.onload = () => {
                    console.log(reader.result);
                    uploadImage(reader.result);
                  }
                }}
              />
              <Button
                color="secondary"
                variant="contained"
                component="span"
                sx={{ mt: 4 }}>
                {`Update Venue Image`}
              </Button>
            </label>
            {/*<Typography variant='body2'>{userInfo?.role}</Typography>*/}
          </Box>
          {/*<CircleProgressWidget
            height={244}
            title={t("profile.completion.title")}
            value={75}
          />*/}
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              textAlign: 'center',
              mb: 6,
            }}
          >
            <QRCode
              value={userInfo ? String(userInfo.venueId) : 'QR Failed'}
              size={256}
            />
          </Box>
        </Grid>
        {/* <Grid item xs={12} md={8} marginTop={3}>
          <Box sx={{ mb: 4 }}>
            <Tabs aria-label='profile nav tabs' value={false}>
              {profileMenuItems.map((item) => (
                <Tab
                  key={item.key}
                  activeClassName='Mui-selected'
                  end={true}
                  component={NavLink}
                  label={t(item.key)}
                  to={item.path}
                />
              ))}
            </Tabs>
          </Box>
          <QueryWrapper>
            <Outlet />
          </QueryWrapper>
        </Grid> */}
      </Grid>
    </React.Fragment>
  );
};

export default Profile;
