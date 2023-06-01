import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import { useTheme } from "@material-ui/core/styles";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Line, LineChart, ResponsiveContainer, Tooltip, XAxis } from "recharts";
import { fetchData } from "../config/request";

const monthData = [
  {
    name: "Jan",
    pv: 2400,
  },
  {
    name: "Feb",
    pv: 1398,
  },
  {
    name: "Mar",
    pv: 9800,
  },
  {
    name: "Apr",
    pv: 3908,
  },
  {
    name: "May",
    pv: 4800,
  },
  {
    name: "Jun",
    pv: 3800,
  },
  {
    name: "Jul",
    pv: 4300,
  },
  {
    name: "Aug",
    pv: 4300,
  },
  {
    name: "Sep",
    pv: 4300,
  },
  {
    name: "Oct",
    pv: 4300,
  },
  {
    name: "Nov",
    pv: 4300,
  },
  {
    name: "Dec",
    pv: 4300,
  },
];

interface ActivityWidgetProps {
  data: any[];
}

const ActivityWidget = (props: ActivityWidgetProps) => {
  const { t } = useTranslation();
  const theme = useTheme();
  const [checkInData, setCheckInData] = useState<any>([]);
  //@ts-ignore
  useEffect(async () => {
    const data = await fetchData(`/api/venues/2/analytics/requests?inAYear=true`, 'GET')
    // console.log(props.data);
    setCheckInData(data.map((item: any, index: number) => {
      return {
        name: monthData[index].name,
        pv: item
      }
    })
    );
  }, []);
  return (
    <Card>
      <CardHeader title={t("dashboard.activity.title")} />
      <CardContent>
        <ResponsiveContainer width="99%" height={244}>
          <LineChart
            width={500}
            height={300}
            data={checkInData}
            margin={{
              top: 5,
              right: 16,
              left: 16,
              bottom: 5,
            }}
          >
            <XAxis
              axisLine={false}
              tick={{ fill: theme.palette.text.secondary, fontSize: 12 }}
              tickLine={false}
              dataKey="name"
            />
            <Tooltip
              contentStyle={{
                borderRadius: 16,
                boxShadow: theme.shadows[3],
                backgroundColor: theme.palette.background.paper,
                borderColor: theme.palette.background.paper,
              }}
            />
            <Line
              name="Value"
              type="monotone"
              dataKey="pv"
              stroke={theme.palette.primary.main}
              strokeWidth={6}
              activeDot={{ r: 8 }}
            />
          </LineChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
};

export default ActivityWidget;
