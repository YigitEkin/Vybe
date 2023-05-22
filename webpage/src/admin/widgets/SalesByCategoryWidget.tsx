import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import { useTheme } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import { Legend, Pie, PieChart, ResponsiveContainer, Tooltip } from 'recharts';

interface SalesByCategoryWidgetProps {
  data: any[];
}
const SalesByCategoryWidget = (props: SalesByCategoryWidgetProps) => {
  const { t } = useTranslation();
  const theme = useTheme();

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

  const data = props.data.map((item, index) => {
    // console.log(item);
    // console.log(index);
    return {
      name: item.name,
      fill: colors[index].fill,
      value: Number(item.value),
    };
  }
  );

  return (
    <Card>
      <CardHeader title={t('dashboard.salesByCategory.title')} />
      <CardContent>
        <ResponsiveContainer width='99%' height={244}>
          <PieChart width={244} height={244}>
            <Pie
              dataKey='value'
              isAnimationActive={false}
              data={data}
              cx='50%'
              cy='50%'
              outerRadius={80}
              stroke={theme.palette.background.paper}
              strokeWidth={8}
            />

            <Tooltip
              contentStyle={{
                borderRadius: 16,
                boxShadow: theme.shadows[3],
                backgroundColor: theme.palette.background.paper,
                borderColor: theme.palette.background.paper,
              }}
              itemStyle={{
                color: theme.palette.text.primary,
              }}
            />
            <Legend wrapperStyle={{ fontSize: 14 }} />
          </PieChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
};

export default SalesByCategoryWidget;
