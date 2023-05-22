import Avatar from "@material-ui/core/Avatar";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import IconButton from "@material-ui/core/IconButton";
import useTheme from "@material-ui/core/styles/useTheme";
import Typography from "@material-ui/core/Typography";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import DashboardIcon from "@material-ui/icons/Dashboard";
import { useTranslation } from "react-i18next";
import { Link as RouterLink } from "react-router-dom";
import { Area, AreaChart, ResponsiveContainer, Tooltip, XAxis } from "recharts";

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

const views = "6.967.431";

interface ViewsWidgetProps {
  data: any[];
  totalCount?: number;
}

const ViewsWidget = (props: ViewsWidgetProps) => {
  const theme = useTheme();
  const { t } = useTranslation();
  const modData = props.data.map((item, index) => {
    return {
      fb: item,
      name: monthData[index].name,
    };
  });
  return (
    <Card>
      <CardContent>
        <Typography
          align="center"
          component="div"
          marginBottom={2}
          variant="body2"
        >
          {t("admin.home.views.unit")}
        </Typography>
        <Typography align="center" component="div" variant="h2">
          {props.data.reduce((a, b) => a + b, 0)}
        </Typography>
        <Box sx={{ height: 224 }}>
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart
              width={500}
              height={400}
              data={modData}
              margin={{
                top: 0,
                right: 0,
                left: 0,
                bottom: 0,
              }}
            >
              <XAxis
                axisLine={false}
                dataKey="name"
                interval="preserveStartEnd"
                tick={{ fill: theme.palette.text.secondary, fontSize: 12 }}
                tickLine={false}
              />
              <Tooltip
                contentStyle={{
                  borderRadius: 16,
                  boxShadow: theme.shadows[3],
                  backgroundColor: theme.palette.background.paper,
                  borderColor: theme.palette.background.paper,
                }}
              />
              <Area
                type="monotone"
                dataKey="fb"
                fill={theme.palette.primary.main}
                fillOpacity={0.3}
                stroke={theme.palette.primary.main}
                strokeWidth={6}
                activeDot={{ r: 8 }}
              />
            </AreaChart>
          </ResponsiveContainer>
        </Box>
        <Card sx={{ bgcolor: "background.default", mt: 5 }}>
          <CardContent sx={{ display: "flex", alignItems: "center" }}>
            <Avatar sx={{ bgcolor: "background.paper", mr: 2 }}>
              <DashboardIcon />
            </Avatar>
            <Box sx={{ flexGrow: 1 }}>
              <Typography component="div" variant="h6">
                {t("admin.home.views.action")}
              </Typography>
            </Box>
            <IconButton
              aria-label="Go to dashboard"
              component={RouterLink}
              to={`/${process.env.PUBLIC_URL}/admin/dashboard`}
            >
              <ChevronRightIcon />
            </IconButton>
          </CardContent>
        </Card>
      </CardContent>
    </Card>
  );
};

export default ViewsWidget;
