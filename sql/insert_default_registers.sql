SET search_path TO matrizcurricular;



INSERT INTO disciplinas (nome) VALUES
    ('Algoritmos'),
    ('Estrutura de Dados'),
    ('Banco de Dados'),
    ('Engenharia de Software'),
    ('Arquitetura de Software'),
    ('Sistemas Operacionais'),
    ('Redes de Computadores'),
    ('Programação Orientada a Objetos'),
    ('Desenvolvimento Web'),
    ('Engenharia de Requisitos'),
    ('Qualidade de Software'),
    ('Compiladores'),
    ('Inteligência Artificial'),
    ('Machine Learning'),
    ('Segurança da Informação');


INSERT INTO cursos (nome) VALUES
    ('Ciência da Computação'),
    ('Engenharia de Software'),
    ('Engenharia da Computação'),
    ('Sistemas de Informação'),
    ('Análise e Desenvolvimento de Sistemas'),
    ('Engenharia Elétrica'),
    ('Engenharia Mecânica'),
    ('Engenharia Civil'),
    ('Matemática Computacional');

INSERT INTO horarios (dia_semana, inicio, fim) VALUES
-- MONDAY (Segunda-feira)
('MONDAY', '07:30', '08:20'), ('MONDAY', '08:20', '09:10'),
('MONDAY', '09:30', '10:20'), ('MONDAY', '10:20', '11:10'),
('MONDAY', '11:20', '12:10'), ('MONDAY', '12:10', '13:00'),
('MONDAY', '13:30', '14:20'), ('MONDAY', '14:20', '15:10'),
('MONDAY', '15:30', '16:20'), ('MONDAY', '16:20', '17:10'),
('MONDAY', '17:20', '18:10'), ('MONDAY', '18:10', '19:00'),
('MONDAY', '19:00', '19:50'), ('MONDAY', '19:50', '20:40'),
('MONDAY', '21:00', '21:50'), ('MONDAY', '21:50', '22:40'),

-- TUESDAY (Terça-feira)
('TUESDAY', '07:30', '08:20'), ('TUESDAY', '08:20', '09:10'),
('TUESDAY', '09:30', '10:20'), ('TUESDAY', '10:20', '11:10'),
('TUESDAY', '11:20', '12:10'), ('TUESDAY', '12:10', '13:00'),
('TUESDAY', '13:30', '14:20'), ('TUESDAY', '14:20', '15:10'),
('TUESDAY', '15:30', '16:20'), ('TUESDAY', '16:20', '17:10'),
('TUESDAY', '17:20', '18:10'), ('TUESDAY', '18:10', '19:00'),
('TUESDAY', '19:00', '19:50'), ('TUESDAY', '19:50', '20:40'),
('TUESDAY', '21:00', '21:50'), ('TUESDAY', '21:50', '22:40'),

-- WEDNESDAY (Quarta-feira)
('WEDNESDAY', '07:30', '08:20'), ('WEDNESDAY', '08:20', '09:10'),
('WEDNESDAY', '09:30', '10:20'), ('WEDNESDAY', '10:20', '11:10'),
('WEDNESDAY', '11:20', '12:10'), ('WEDNESDAY', '12:10', '13:00'),
('WEDNESDAY', '13:30', '14:20'), ('WEDNESDAY', '14:20', '15:10'),
('WEDNESDAY', '15:30', '16:20'), ('WEDNESDAY', '16:20', '17:10'),
('WEDNESDAY', '17:20', '18:10'), ('WEDNESDAY', '18:10', '19:00'),
('WEDNESDAY', '19:00', '19:50'), ('WEDNESDAY', '19:50', '20:40'),
('WEDNESDAY', '21:00', '21:50'), ('WEDNESDAY', '21:50', '22:40'),

-- THURSDAY (Quinta-feira)
('THURSDAY', '07:30', '08:20'), ('THURSDAY', '08:20', '09:10'),
('THURSDAY', '09:30', '10:20'), ('THURSDAY', '10:20', '11:10'),
('THURSDAY', '11:20', '12:10'), ('THURSDAY', '12:10', '13:00'),
('THURSDAY', '13:30', '14:20'), ('THURSDAY', '14:20', '15:10'),
('THURSDAY', '15:30', '16:20'), ('THURSDAY', '16:20', '17:10'),
('THURSDAY', '17:20', '18:10'), ('THURSDAY', '18:10', '19:00'),
('THURSDAY', '19:00', '19:50'), ('THURSDAY', '19:50', '20:40'),
('THURSDAY', '21:00', '21:50'), ('THURSDAY', '21:50', '22:40'),

-- FRIDAY (Sexta-feira)
('FRIDAY', '07:30', '08:20'), ('FRIDAY', '08:20', '09:10'),
('FRIDAY', '09:30', '10:20'), ('FRIDAY', '10:20', '11:10'),
('FRIDAY', '11:20', '12:10'), ('FRIDAY', '12:10', '13:00'),
('FRIDAY', '13:30', '14:20'), ('FRIDAY', '14:20', '15:10'),
('FRIDAY', '15:30', '16:20'), ('FRIDAY', '16:20', '17:10'),
('FRIDAY', '17:20', '18:10'), ('FRIDAY', '18:10', '19:00'),
('FRIDAY', '19:00', '19:50'), ('FRIDAY', '19:50', '20:40'),
('FRIDAY', '21:00', '21:50'), ('FRIDAY', '21:50', '22:40');


INSERT INTO professores (nome) VALUES
    ('Carlos Henrique Silva'),
    ('Ana Paula Costa'),
    ('Roberto Nogueira'),
    ('Fernanda Lima'),
    ('João Batista Rocha');

