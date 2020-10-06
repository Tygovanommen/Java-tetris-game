-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `levels` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rows` int(11) NOT NULL,
  `speed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `levels` (`id`, `name`, `rows`, `speed`) VALUES
(1, 'Easy', 0, 1000),
(2, 'Medium', 2, 800),
(3, 'Hard', 4, 500),
(4, 'Super hard', 5, 200);

CREATE TABLE `scores` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `level_id` int(11) NOT NULL,
  `datatime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `vHighscores` (
`username` varchar(50)
,`score` int(11)
,`levelname` varchar(50)
);

DROP TABLE IF EXISTS `vHighscores`;

CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `vHighscores`  AS  select `users`.`name` AS `username`,`scores`.`score` AS `score`,`levels`.`name` AS `levelname` from ((`scores` join `users` on(`scores`.`user_id` = `users`.`id`)) join `levels` on(`scores`.`level_id` = `levels`.`id`)) ;

ALTER TABLE `levels`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `scores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `users` (`user_id`),
  ADD KEY `levels` (`level_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `levels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `scores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

ALTER TABLE `scores`
  ADD CONSTRAINT `levels` FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`),
  ADD CONSTRAINT `users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;