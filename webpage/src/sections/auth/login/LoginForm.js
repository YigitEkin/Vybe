/*eslint-disable*/
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// @mui
import { Link, Stack, IconButton, InputAdornment, TextField, Checkbox } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import account from 'src/_mock/account';
// components
import Iconify from '../../../components/iconify';
import useAuthStore from 'src/stores/auth';

// ----------------------------------------------------------------------

export default function LoginForm() {
  const navigate = useNavigate();
  const { email, setEmail, password, setPassword, isLoggedIn, setIsLoggedIn } = useAuthStore((state) => state);

  const [showPassword, setShowPassword] = useState(false);

  console.log('email: ', email);
  console.log('password: ', password);
  const handleClick = () => {
    if (email && password) {
      setIsLoggedIn(true);
      navigate('/dashboard/app', { replace: true });
    }
  };

  return (
    <>
      <Stack spacing={3}>
        <TextField name="email" label="Email address" onChange={(val) => setEmail(val.target.value)} />

        <TextField
          name="password"
          label="Password"
          onChange={(val) => setPassword(val.target.value)}
          type={showPassword ? 'text' : 'password'}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
      </Stack>

      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}>
        <Checkbox name="remember" label="Remember me" />
        <Link variant="subtitle2" underline="hover">
          Forgot password?
        </Link>
      </Stack>

      <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
        Login
      </LoadingButton>
    </>
  );
}
