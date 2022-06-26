-- noinspection SqlNoDataSourceInspectionForFile

insert into team (name) values ('Chelsea');
insert into team (name) values ('Arsenal');
insert into team (name) values ('Manchester City');
insert into team (name) values ('Liverpool');

insert into team_stats (team_id, average_goals_scored, average_goals_conceeded)
values (
    (select id from team where name = 'Chelsea'),
    1.71,
    1.125
);

insert into team_stats (team_id, average_goals_scored, average_goals_conceeded)
values (
    (select id from team where name = 'Arsenal'),
    1.75,
    1.148
);

insert into team_stats (team_id, average_goals_scored, average_goals_conceeded)
values (
    (select id from team where name = 'Manchester City'),
    1.72,
    1.068
);

insert into team_stats (team_id, average_goals_scored, average_goals_conceeded)
values (
    (select id from team where name = 'Liverpool'),
    1.75,
    1.147
);