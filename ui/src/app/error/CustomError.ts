export class CustomError extends Error {
  constructor(
    message: string,
    public statusMessage: string,
    public statusCode: number
  ) {
    super(message)
    this.name = 'CustomError'
  }
}
