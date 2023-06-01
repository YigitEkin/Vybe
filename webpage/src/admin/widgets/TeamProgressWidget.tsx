import Box from '@material-ui/core/Box';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import LinearProgress from '@material-ui/core/LinearProgress';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import { useTranslation } from 'react-i18next';

const teams = [
  {
    id: '1',
    color: 'primary.main',
    name: 'Askin Olayim',
    progress: 52,
    value: 122,
  },
  {
    id: '2',
    color: 'warning.main',
    name: 'Simply Falling',
    progress: 33,
    value: 82,
  },
  {
    id: '3',
    color: 'error.main',
    name: 'Atesini Yolla Bana',
    progress: 10,
    value: 39,
  },
  {
    id: '4',
    color: 'text.secondary',
    name: 'Wherever I Go',
    progress: 5,
    value: 9,
  },
];

const colors = [
  {
    fill: '#EA34C9',
  },
  {
    fill: '#9F4AD7',
  },
  {
    fill: '#6386E6',
  },
  {
    fill: '#5BC8FA',
  },
];

interface TeamProgressWidgetProps {
  data: any[];
  count: number;
}

const TeamProgressWidget = (props: TeamProgressWidgetProps) => {
  const { t } = useTranslation();

  return (
    <Card>
      <CardHeader title={t('dashboard.teams.title')} />
      <CardContent sx={{ px: 2 }}>
        <TableContainer>
          <Table
            aria-label='team progress table'
            size='small'
            sx={{
              '& td, & th': {
                border: 0,
              },
            }}
          >
            <TableHead>
              <TableRow>
                <TableCell>{t('dashboard.teams.columns.team')}</TableCell>
                <TableCell>{t('dashboard.teams.columns.progress')}</TableCell>
                <TableCell align='center'>
                  {t('dashboard.teams.columns.value')}
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {props.data.slice(0, 4).map((song, index) => (
                <TableRow key={song[0].id}>
                  <TableCell>
                    <Typography color='text.secondary' component='div'>
                      {song[0].name}
                    </Typography>
                  </TableCell>
                  <TableCell>
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                      <Box sx={{ width: '100%', mr: 3 }}>
                        <LinearProgress
                          aria-label={`${song[0].name} progress`}
                          color='inherit'
                          sx={{ color: colors[index].fill }}
                          value={((song[1] * (100)) / (props.count))}
                          variant='determinate'
                        />
                      </Box>
                      <Box sx={{ minWidth: 35 }}>
                        <Typography
                          component='span'
                          variant='h6'
                          color={colors[index].fill}
                        >{`${((song[1] * (100)) / (props.count)).toFixed(2)}%`}</Typography>
                      </Box>
                    </Box>
                  </TableCell>
                  <TableCell align='center'>{song[1]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </CardContent>
    </Card>
  );
};

export default TeamProgressWidget;
