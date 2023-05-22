import Avatar from "@material-ui/core/Avatar";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import ListItemText from "@material-ui/core/ListItemText";
import { useTheme } from "@material-ui/core/styles";
import { useTranslation } from "react-i18next";

const users = [
  {
    id: "1",
    firstName: "Rhys",
    gender: "M",
    lastName: "Arriaga",
    role: "Admin",
  },
  {
    id: "2",
    firstName: "Laura",
    gender: "F",
    lastName: "Core",
    role: "Member",
  },
  {
    id: "3",
    firstName: "Joshua",
    gender: "M",
    lastName: "Jagger",
    role: "Member",
  },
];

interface UserWidgetProps {
  data: any[];
}

const UsersWidget = (props: UserWidgetProps) => {
  const theme = useTheme();
  const { t } = useTranslation();

  return (
    <Card>
      <CardHeader title={t("dashboard.users.title")} />
      <CardContent>
        <List>
          {props.data.map((song) => (
            <ListItem disableGutters key={song.id}>
              <ListItemAvatar>
                <Avatar>
                  <img
                    style={{ contain: "cover", width: "100%", height: "100%" }}
                    src={song.link} />
                </Avatar>
              </ListItemAvatar>
              <ListItemText
                primary={`${song.name}`}
                primaryTypographyProps={{
                  fontWeight: theme.typography.fontWeightMedium,
                }}
                secondary={song.artist}
              />
              {/* <ListItemSecondaryAction>
                <IconButton
                  aria-label="Go to user details"
                  component={RouterLink}
                  edge="end"
                  to={`/${process.env.PUBLIC_URL}/admin/user-management`}
                >
                  <ChevronRightIcon />
                </IconButton>
              </ListItemSecondaryAction> */}
            </ListItem>
          ))}
        </List>
      </CardContent>
    </Card>
  );
};

export default UsersWidget;
