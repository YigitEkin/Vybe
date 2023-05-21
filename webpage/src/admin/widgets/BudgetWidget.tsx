import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import { useTheme } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import {
  PolarAngleAxis,
  Radar,
  RadarChart,
  ResponsiveContainer,
  Tooltip,
} from 'recharts';

const data3 = [
  {
    subject: '00:00-04:00',
    A: 110,
  },
  {
    subject: '04:00-08:00',
    A: 98,
  },
  {
    subject: '08:00-12:00',
    A: 86,
  },
  {
    subject: '12:00-16:00',
    A: 99,
  },
  {
    subject: '16:00-20:00',
    A: 85,
  },
  {
    subject: '20:00-00:00',
    A: 65,
  },
];
interface BudgetWidgetProps {
  data: number[];
}
const BudgetWidget = (data: BudgetWidgetProps) => {
  const { t } = useTranslation();
  const theme = useTheme();
  const data2: any = data.data.map((item: any, index: number) => {
    return {
      subject: data3[index].subject,
      A: item,
    };
  });

  return (
    <Card>
      <CardHeader title={t('dashboard.budget.title')} />
      <CardContent>
        <ResponsiveContainer width='99%' height={244}>
          <RadarChart cx='50%' cy='50%' outerRadius='80%' data={data2}>
            <PolarAngleAxis
              dataKey='subject'
              tick={{ fill: theme.palette.text.secondary, fontSize: 14 }}
            />
            <Radar
              name={t('dashboard.budget.legend.unit')}
              dataKey='A'
              stroke={theme.palette.primary.main}
              strokeWidth={8}
              fill={theme.palette.primary.main}
            />
            <Tooltip
              contentStyle={{
                borderRadius: 16,
                boxShadow: theme.shadows[3],
                backgroundColor: theme.palette.background.paper,
                borderColor: theme.palette.background.paper,
              }}
            />
          </RadarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
};

export default BudgetWidget;
