import { ObjectId } from './object-id';

describe('ObjectId', () => {
  it('should create an instance', () => {
    expect(new ObjectId()).toBeTruthy();
  });
});
