-- First, insert categories
INSERT INTO categories (id, name, description) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'Breakfast', 'Morning meals served from 7AM to 10:30AM. Complete breakfast options.'),
('550e8400-e29b-41d4-a716-446655440001', 'Lunch', 'Main dining period from 11:30AM to 2:30PM. Complete meals with main course.'),
('550e8400-e29b-41d4-a716-446655440002', 'Snacks', 'Available throughout operating hours. Quick bites and light refreshments.'),
('550e8400-e29b-41d4-a716-446655440003', 'Beverages', 'Hot and cold drinks available throughout operating hours.'),
('550e8400-e29b-41d4-a716-446655440004', 'Side Dishes', 'Complementary dishes to accompany main meals.'),
('550e8400-e29b-41d4-a716-446655440005', 'Desserts', 'Sweet treats and dessert options.'),
('550e8400-e29b-41d4-a716-446655440006', 'VIP Menu', 'Special pre-order menu items with premium ingredients and preparation.');

-- Then insert articles with matching category IDs
INSERT INTO articles (id, title, description, price, category_id) VALUES
-- Breakfast Items (using breakfast category ID)
('660e8400-e29b-41d4-a716-446655440000', 'Moroccan Msemen', 'Fresh flatbread served with honey and butter', 15.00, '550e8400-e29b-41d4-a716-446655440000'),
('660e8400-e29b-41d4-a716-446655440001', 'Baghrir', 'Thousand-hole pancake with butter and honey', 12.00, '550e8400-e29b-41d4-a716-446655440000'),

-- Lunch Items (using lunch category ID)
('660e8400-e29b-41d4-a716-446655440002', 'Couscous Royal', 'Traditional Friday couscous with vegetables and meat', 45.00, '550e8400-e29b-41d4-a716-446655440001'),
('660e8400-e29b-41d4-a716-446655440003', 'Tajine Kefta', 'Meatballs with eggs in tomato sauce', 40.00, '550e8400-e29b-41d4-a716-446655440001'),

-- Beverages (using beverages category ID)
('660e8400-e29b-41d4-a716-446655440004', 'Moroccan Mint Tea', 'Traditional green tea with fresh mint', 8.00, '550e8400-e29b-41d4-a716-446655440003'),
('660e8400-e29b-41d4-a716-446655440005', 'Fresh Orange Juice', 'Freshly squeezed Moroccan oranges', 12.00, '550e8400-e29b-41d4-a716-446655440003');

-- Insert cards
INSERT INTO cards (id, balance, last_update_date) VALUES
('770e8400-e29b-41d4-a716-446655440000', 1000.00, CURRENT_DATE()),
('770e8400-e29b-41d4-a716-446655440001', 200.00, CURRENT_DATE());

-- Insert users
INSERT INTO users (id, first_name, last_name, email, password, role, card_id) VALUES
('880e8400-e29b-41d4-a716-446655440000', 'Mohammed', 'Alami', 'm.alami@usms.ac.ma', '$2a$10$YJeuGf3U7YFAFTPZt3F.xONWPfhxn2oXzPAMBPHPYGv0WTKhPCydq', 'MANAGER', '770e8400-e29b-41d4-a716-446655440000'),
('880e8400-e29b-41d4-a716-446655440001', 'Fatima', 'Benani', 'f.benani@usms.ac.ma', '$2a$10$YJeuGf3U7YFAFTPZt3F.xONWPfhxn2oXzPAMBPHPYGv0WTKhPCydq', 'USER', '770e8400-e29b-41d4-a716-446655440001');